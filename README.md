# CircularViewPager

## Known Issues
  - If there are less than `CircularViewPager.getOffscreenPageLimit() * 2 + 1` items in the adapter, scrolling forward will animate incorrectly
  - Changing the default offscreen page limit has not been tested yet, although it should work