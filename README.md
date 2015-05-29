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
  - If there are less than `CircularViewPager.getOffscreenPageLimit() * 2 + 1` items in the adapter, scrolling forward will animate incorrectly
  - Changing the default offscreen page limit has not been tested yet, although it should work