package asiantech.internship.summer.thachnguyen.debug.viewpager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.TimelineAdapter;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.TimelineItemFragment;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;

public class PagerActivity extends AppCompatActivity implements TimelineAdapter.OnLikeClickListener, FavouriteFragment.OnUnlikeClickListener, FavouriteFragment.RemoveAllList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLikeClickListener(TimelineItem timelineItem) {
        FavouriteFragment favouriteFragment = (FavouriteFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
        favouriteFragment.display(timelineItem);
    }

    @Override
    public void onUnlikeClickListener(TimelineItem timelineItem) {
        TimelineItemFragment timelineItemFragment = (TimelineItemFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
        timelineItemFragment.setLike(timelineItem);
    }

    @Override
    public void removeAllList() {
        FavouriteFragment favouriteFragment = (FavouriteFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
        favouriteFragment.removeAll();
    }
}
