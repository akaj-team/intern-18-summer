package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;
import asiantech.internship.summer.thachnguyen.debug.viewpager.FavouriteFragment;

public class RecyclerViewActivity extends AppCompatActivity implements TimelineAdapter.OnLikeClickListener, FavouriteFragment.Refresh {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Time Line");

        TimelineItemFragment timelineItemFragment = new TimelineItemFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, timelineItemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onLikeClickListener(TimelineItem timelineItem) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        TimelineItemFragment timelineItemFragment = (TimelineItemFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        timelineItemFragment.messageFavourite(timelineItem);
    }

    @Override
    public void refresh() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        TimelineItemFragment timelineItemFragment = (TimelineItemFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        timelineItemFragment.messageRefresh();
    }
}
