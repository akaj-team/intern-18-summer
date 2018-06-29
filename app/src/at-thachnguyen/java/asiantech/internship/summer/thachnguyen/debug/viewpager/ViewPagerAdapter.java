package asiantech.internship.summer.thachnguyen.debug.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import asiantech.internship.summer.thachnguyen.debug.recyclerview.TimelineItemFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new TimelineItemFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
