package asiantech.internship.summer.viewpager.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.recyclerview.TimelineFragment;
import asiantech.internship.summer.viewpager.FavouriteFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final String[] TAB_TITLES = {"Timeline", "Favourite"};
    private final List<Fragment> mListFragment = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        addFragment();
    }

    private void addFragment() {
        mListFragment.add(new TimelineFragment());
        mListFragment.add(new FavouriteFragment());
    }

    public Fragment getFragment(int position) {
        return mListFragment.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
