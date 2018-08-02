package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.viewpager.adapter.ViewpagerAdapter;
import asiantech.internship.summer.timeline.TimelineFragment;
import asiantech.internship.summer.timeline.model.TimelineItem;

public class ViewPagerActivity extends AppCompatActivity implements TimelineFragment.OnTimelineListener {
    public static final String TITLE = "VIEWPAGER TAB-LAYOUT";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private TimelineFragment mTimelineFragment = TimelineFragment.newInstance(1);
    private TimelineFragment mFavouriteFragment = TimelineFragment.newInstance(2);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        initView();

        List<TimelineFragment> listFragment = new ArrayList<>();
        listFragment.add(mTimelineFragment);
        listFragment.add(mFavouriteFragment);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), listFragment);
        mViewPager.setAdapter(viewpagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarViewPager);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    public void likeItemTimeline(TimelineItem timelineItem) {
        mFavouriteFragment.receivedDataFavourite(timelineItem);
    }

    @Override
    public void dislikeItemTimeline(TimelineItem timelineItem) {
        mFavouriteFragment.removeDataFavourite(timelineItem);
    }

    @Override
    public void removeItemFavorite(TimelineItem timelineItem) {
        mTimelineFragment.moveStateLikeTimeLine(timelineItem);
    }

    @Override
    public void removeAllDataFavourite() {
        mFavouriteFragment.removeAll();
    }
}
