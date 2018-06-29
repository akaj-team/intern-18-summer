package asiantech.internship.summer.thachnguyen.debug.viewpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import asiantech.internship.summer.thachnguyen.debug.recyclerview.TimelineItemFragment;

public class PagerAdapter extends FragmentPagerAdapter {
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
        if (position == 0) {
            return "Time Line";
        } else {
            return "Favourite";
        }
    }
}
