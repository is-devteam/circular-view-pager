# CircularViewPager

## Usage
Use the following in your layout instead of `ViewPager`:

``` java
<com.isapp.android.circularviewpager.CircularViewPager
    android:id="@+id/pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
```

Also, use `CircularViewPager.*` for nested classes instead of `ViewPager.*` (eg `CircularViewPager.OnPageChangeListener` instead of `ViewPager.OnPageChangeListener`).

## Gradle

        compile 'com.isapp.android:circular-view-pager:0.0.2-SNAPSHOT'

## Known Issues
  - If there are 2 items in the adapter, and the `CircularViewPager` is swiped, the next item won't display until the swipe is finished
  - If there are 4 items in the adapter, and `offscreenPageLimit` >= 2, and the `CircularViewPager` is swiped to the last item, the last item won't display until the swipe is finished
  - There are states where if `CircularViewPager.setOffscreenPageLimit()` is called with a new value, interaction with the `PagerAdapter` can enter a bad state
    - One possible implementation fix is to destroy all items when the offscreen page limit is changed (not the best option)
    - If this is occurring, a workaround is to call `CircularViewPager.setAdapter(null)`, then call `CircularViewPager.setOffscreenPageLimit(limit)`, and then call `CircularViewPager.setAdapter(adapter)`
