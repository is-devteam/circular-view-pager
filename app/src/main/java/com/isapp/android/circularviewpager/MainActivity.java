package com.isapp.android.circularviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
  public static final int INVALID_INDEX = -1;
  public static final int DEFAULT_SIZE = 10;

  private CircularViewPager circularViewPager;
  private TextView currentPositionView;

  private EditText insertPageIndexView;
  private Button insertPageButton;

  private EditText removePageIndexView;
  private Button removePageButton;

  private EditText gotoPageIndexView;
  private Button gotoPageButton;

  private EditText resetSizeView;
  private Button resetButton;

  private Toast toast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    circularViewPager = (CircularViewPager) findViewById(R.id.circular_view_pager);
    circularViewPager.setAdapter(new Adapter(DEFAULT_SIZE));

    circularViewPager.setCurrentItem(0);

    circularViewPager.setOnPageChangeListener(new CircularViewPager.OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        setCurrentPositionText(position);
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });

    currentPositionView = (TextView) findViewById(R.id.current_page);
    setCurrentPositionText(0);

    insertPageIndexView = (EditText) findViewById(R.id.insert_page_index);
    insertPageButton = (Button) findViewById(R.id.insert_page_button);

    removePageIndexView = (EditText) findViewById(R.id.remove_page_index);
    removePageButton = (Button) findViewById(R.id.remove_page_button);

    gotoPageIndexView = (EditText) findViewById(R.id.goto_page_index);
    gotoPageButton = (Button) findViewById(R.id.goto_page_button);

    resetSizeView = (EditText) findViewById(R.id.reset_size);
    resetSizeView.setText(Integer.toString(DEFAULT_SIZE));
    resetButton = (Button) findViewById(R.id.reset_button);

    insertPageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissKeyboard(v);

        String indexStr = insertPageIndexView.getText().toString();
        int index = parseIndexString(indexStr);
        if(index != INVALID_INDEX) {
          ((Adapter) circularViewPager.getAdapter()).insertAt(index);
        }
      }
    });

    removePageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissKeyboard(v);

        String indexStr = removePageIndexView.getText().toString();
        int index = parseIndexString(indexStr);
        if(index != INVALID_INDEX) {
          ((Adapter) circularViewPager.getAdapter()).removeFrom(index);
        }
      }
    });

    gotoPageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissKeyboard(v);

        String indexStr = gotoPageIndexView.getText().toString();
        int index = parseIndexString(indexStr);
        if(index != INVALID_INDEX) {
          circularViewPager.setCurrentItem(index, true);
        }
      }
    });

    resetButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissKeyboard(v);

        String indexStr = resetSizeView.getText().toString();
        int size = 0;
        try {size = Integer.parseInt(indexStr);}catch(NumberFormatException ignore) {}
        if(size <= 0) {
          if(toast != null) {
            toast.cancel();
          }
          toast = Toast.makeText(MainActivity.this, R.string.adapter_size_error, Toast.LENGTH_SHORT);
          toast.show();
        }
        else {
          circularViewPager.setAdapter(new Adapter(size));
          setCurrentPositionText(0);
        }
      }
    });
  }

  private void dismissKeyboard(View v) {
    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
  }

  private int parseIndexString(String indexStr) {
    int index = INVALID_INDEX;
    try {index = Integer.parseInt(indexStr);}catch(NumberFormatException ignore) {}
    if(index >= 0 && index < circularViewPager.getAdapter().getCount()) {
      return index;
    }
    else {
      if(toast != null) {
        toast.cancel();
      }
      toast = Toast.makeText(MainActivity.this, String.format(getString(R.string.invalid_index_toast), indexStr), Toast.LENGTH_SHORT);
      toast.show();
      return INVALID_INDEX;
    }
  }

  private void setCurrentPositionText(int position) {
    currentPositionView.setText(String.format(getString(R.string.current_page_indicator), position));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public static class PagerFragment extends Fragment {
    public PagerFragment() {}

    public static PagerFragment newInstance(int position) {
      PagerFragment frag = new PagerFragment();
      Bundle args = new Bundle();
      args.putInt("position", position);
      frag.setArguments(args);
      return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      TextView tv = new TextView(getActivity());
      tv.setText(Integer.toString(getArguments().getInt("position", -1)));
      return tv;
    }
  }

  private class Adapter extends FragmentStatePagerAdapter {
    private List<Integer> items;

    public Adapter(int size) {
      super(getSupportFragmentManager());

      items = new ArrayList<>(size);
      for(int i = 0; i < size; i++) {
        items.add(i);
      }
    }

    public void insertAt(int position) {
      items.add(position, position);
      for(int i = position + 1; i < items.size(); i++) {
        items.set(i, i);
      }
      notifyDataSetChanged();
    }

    public void removeFrom(int position) {
      items.remove(position);
      for(int i = position; i < items.size(); i++) {
        items.set(i, i);
      }
      notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
      return PagerFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
      return items.size();
    }
  }
}
