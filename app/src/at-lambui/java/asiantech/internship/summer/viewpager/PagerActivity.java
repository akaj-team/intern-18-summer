package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.TimelineFragment;
import asiantech.internship.summer.recyclerview.model.TimelineItem;
import asiantech.internship.summer.viewpager.adapter.PagerAdapter;

public class PagerActivity extends AppCompatActivity {

    private TimelineFragment mrecyclerViewFragment;
    private FavouriteFragment mfavouriteFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mrecyclerViewFragment = ((TimelineFragment) (pagerAdapter.getFragment(0)));
        mfavouriteFragment = ((FavouriteFragment) (pagerAdapter.getFragment(1)));
        mrecyclerViewFragment.setListener(position -> {
            TimelineItem timelineItem = mrecyclerViewFragment.timelineItems.get(position);
            boolean islike = timelineItem.ismIsLiked();
            if (islike) {
                mfavouriteFragment.addItem(timelineItem);
            } else {
                mfavouriteFragment.removeItem(timelineItem);
            }
        });
        mfavouriteFragment.setListener(position -> {
            mfavouriteFragment.removeItem(position);
            mrecyclerViewFragment.getAdapter().notifyDataSetChanged();
        });
    }
}
