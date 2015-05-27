package com.isapp.android.circularviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
  private CircularViewPager circularViewPager;
  private TextView currentPositionView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    circularViewPager = (CircularViewPager) findViewById(R.id.circular_view_pager);
    circularViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return PagerFragment.newInstance(position);
      }

      @Override
      public int getCount() {
        return 10;
      }
    });

    circularViewPager.setCurrentItem(0);

    circularViewPager.setOnPageChangeListener(new CircularViewPager.OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        setCurrentPositionText(position);
      }

      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
      @Override public void onPageScrollStateChanged(int state) {}
    });

    currentPositionView = (TextView) findViewById(R.id.current_page);
    setCurrentPositionText(0);
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
}
