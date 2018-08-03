package asiantech.internship.summer.viewpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import asiantech.internship.summer.timeline.TimelineFragment;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private List<TimelineFragment> mTimelineFragments;
    private static final String[] TAB_TITLES = {
            "Timeline",
            "Favourite"
    };

    public ViewpagerAdapter(FragmentManager fm, List<TimelineFragment> timelineList) {
        super(fm);
        this.mTimelineFragments = timelineList;
    }

    @Override
    public Fragment getItem(int position) {
        return mTimelineFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTimelineFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
