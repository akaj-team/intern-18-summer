package asiantech.internship.summer.viewpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import asiantech.internship.summer.recyclerview.TimelineItemFragment;

class PagerAdapter extends FragmentPagerAdapter {
    private static final String TAB_TITLES[] = {"Time Line", "Favourite"};

    PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TimelineItemFragment();
        } else {
            return new FavouriteFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
