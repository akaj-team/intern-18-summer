package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.Objects;
import java.util.Random;
import asiantech.internship.summer.R;

public class RecyclerViewActivity extends AppCompatActivity {
    private static String mPackagename;
    private static Resources mResources;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mPackagename = getPackageName();
        mResources = getResources();
        mContext = getApplicationContext();

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

    public static int randomImageFood(String image, int i) {
        Random rand = new Random();
        int rndN = rand.nextInt(i) + 1;
        String imgName = "img_" + image + rndN;
        return mResources.getIdentifier(imgName, "drawable", mPackagename);
    }


}
