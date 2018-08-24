package asiantech.internship.summer.drawer_layout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.drawer_recyler_view.DrawerAdapter;
import asiantech.internship.summer.drawer_layout.model.DrawerHeader;
import asiantech.internship.summer.drawer_layout.model.DrawerItem;
import asiantech.internship.summer.drawer_layout.utils.IntentUtils;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerClickedListener {

    private final int PERMISSION_REQUEST_CODE = 1656;
    private final int GET_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    private List<Object> mDataSet;
    private DrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        mDataSet = new ArrayList<>();
        setUpDataSet();
        setUpRecyclerView();
    }

    private void setUpDataSet() {
        mDataSet.add(new DrawerHeader(R.drawable.img_avatar_1, R.array.email_list));
        mDataSet.add(new DrawerItem(R.drawable.ic_inbox, R.string.feature_inbox));
        mDataSet.add(new DrawerItem(R.drawable.ic_outbox, R.string.feature_outbox));
        mDataSet.add(new DrawerItem(R.drawable.ic_trash, R.string.feature_trash));
        mDataSet.add(new DrawerItem(R.drawable.ic_spam, R.string.feature_spam));
    }

    private void setUpRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDrawerAdapter = new DrawerAdapter(mDataSet, this, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mDrawerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == GET_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = imageReturnedIntent.getData();
            DrawerHeader drawerHeader = (DrawerHeader) mDataSet.get(0);
            drawerHeader.setUri(selectedImageUri);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                mDrawerAdapter.notifyItemChanged(0);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mDrawerAdapter.notifyItemChanged(0);
            } else {
                DrawerHeader drawerHeader = (DrawerHeader) mDataSet.get(0);
                drawerHeader.setUri(null);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults);
        }
    }

    @Override
    public void onDrawerItemClicked(int featureNameId) {
        for (int i = 1; i < mDataSet.size(); i++) {
            DrawerItem drawerItem = (DrawerItem) mDataSet.get(i);
            drawerItem.setIsSelected(drawerItem.getTitleId() == featureNameId);
        }
        mDrawerAdapter.notifyDataSetChanged();
        String featureName = this.getResources().getString(featureNameId);
        Toast.makeText(this, featureName, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDrawerHeaderImageClicked() {
        Intent intent = IntentUtils.getPickImageIntent(this);
        if (intent != null) {
            this.startActivityForResult(intent, GET_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
}
