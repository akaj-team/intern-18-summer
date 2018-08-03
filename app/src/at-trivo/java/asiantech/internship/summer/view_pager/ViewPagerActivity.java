package asiantech.internship.summer.view_pager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recycler_view.TimeLineFragment;
import asiantech.internship.summer.recycler_view.model.Timeline;
import asiantech.internship.summer.view_pager.timeline_view_pager.FavoriteTimelineFragment;
import asiantech.internship.summer.view_pager.timeline_view_pager.MainTimelineFragment;
import asiantech.internship.summer.view_pager.timeline_view_pager.TimelinePagerAdapter;

public class ViewPagerActivity extends AppCompatActivity implements TimeLineFragment.TimelineFragmentListener {

    private ViewPager mViewPager;
    private TimelinePagerAdapter mTimelinePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        setUpPageAdapter();
    }

    private void setUpPageAdapter() {
        mTimelinePagerAdapter = new TimelinePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTimelinePagerAdapter);
    }

    @Override
    public void onHeartImageClick(Class fragmentClass, int position) {
        FavoriteTimelineFragment favoriteTimelineFragment = mTimelinePagerAdapter.getFavoriteTimelineFragment();
        MainTimelineFragment mainTimelineFragment = mTimelinePagerAdapter.getMainTimelineFragment();

        if (fragmentClass.equals(FavoriteTimelineFragment.class)) {
            Timeline timeline = favoriteTimelineFragment.removeTimelineAt(position);
            mainTimelineFragment.changeLovedStatus(timeline);
        } else {
            Timeline timeline = mainTimelineFragment.getTimelineAt(position);
            if (timeline.isLoved()) {
                favoriteTimelineFragment.addTimeline(timeline);
            } else {
                favoriteTimelineFragment.removeTimeline(timeline);
            }
        }
    }
}
