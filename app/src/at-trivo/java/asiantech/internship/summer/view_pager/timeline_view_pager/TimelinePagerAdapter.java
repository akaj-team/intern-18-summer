package asiantech.internship.summer.view_pager.timeline_view_pager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TimelinePagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] mFragments = new Fragment[2];
    private final int MAIN_TIMELINE_FRAGMENT_INDEX = 0;
    private final int FAVORITE_TIMELINE_FRAGMENT_INDEX = 1;
    private final String[] TAB_TITLES = {"Timeline", "Favourite"};

    public TimelinePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        setFragments();
    }

    private void setFragments() {
        mFragments[MAIN_TIMELINE_FRAGMENT_INDEX] = new MainTimelineFragment();
        mFragments[FAVORITE_TIMELINE_FRAGMENT_INDEX] = new FavoriteTimelineFragment();
    }

    public MainTimelineFragment getMainTimelineFragment() {
        return (MainTimelineFragment) mFragments[MAIN_TIMELINE_FRAGMENT_INDEX];
    }

    public FavoriteTimelineFragment getFavoriteTimelineFragment() {
        return (FavoriteTimelineFragment) mFragments[FAVORITE_TIMELINE_FRAGMENT_INDEX];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
