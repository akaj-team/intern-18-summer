package asiantech.internship.summer.timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import asiantech.internship.summer.R;

public class TimelineActivity extends AppCompatActivity {
    private static final String TITLE_TOOLBAR = "Timeline Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = findViewById(R.id.toolbarRecyclerView);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);

        replaceFragment();
    }

    private void replaceFragment() {
        TimelineFragment timelineFragment = new TimelineFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContainer, timelineFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
