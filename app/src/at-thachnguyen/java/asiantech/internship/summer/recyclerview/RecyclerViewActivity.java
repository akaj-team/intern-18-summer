package asiantech.internship.summer.recyclerview;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.Objects;
import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.model.TimelineItem;
import asiantech.internship.summer.viewpager.FavouriteFragment;

@SuppressLint("Registered")
public class RecyclerViewActivity extends AppCompatActivity implements FavouriteFragment.OnPullRefreshRecyclerView, TimelineItemFragment.OnChangeFavourite {
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
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            finish();
            return;
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void refresh() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        TimelineItemFragment timelineItemFragment = (TimelineItemFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        timelineItemFragment.messageRefresh();
    }

    @Override
    public void onChangeFavourite(TimelineItem timelineItem) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        TimelineItemFragment timelineItemFragment = (TimelineItemFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        timelineItemFragment.onChangeLike(timelineItem);
    }
}
