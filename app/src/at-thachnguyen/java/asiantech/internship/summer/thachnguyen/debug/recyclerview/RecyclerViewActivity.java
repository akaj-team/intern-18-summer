package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    public static String getName(int i) {
        String[] arrayNames = mContext.getResources().getStringArray(R.array.name);
        return arrayNames[i];
    }

    public static int getAvatar(int i) {
        String imgName = "img_avt" + i;
        return mResources.getIdentifier(imgName, "drawable", mPackagename);
    }

    public static String getDescription(int i) {
        String[] arrayDescriptions = mContext.getResources().getStringArray(R.array.description);
        return arrayDescriptions[i];
    }
}