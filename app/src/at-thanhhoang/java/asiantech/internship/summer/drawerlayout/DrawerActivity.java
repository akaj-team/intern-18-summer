package asiantech.internship.summer.drawerlayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.adapter.DrawerAdapter;
import asiantech.internship.summer.drawerlayout.models.OptionData;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("Registered")
public class DrawerActivity extends AppCompatActivity implements DrawerAdapter.ClickListener {
    private static final String TITLE_TOOLBAR_DRAWER_LAYOUT = "Drawer Layout";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_LOAD_IMG = 2;
    private static final String TITLE_OPTION_DIALOG = "Option";
    private static final String TAG = DrawerActivity.class.getSimpleName();

    private final CharSequence[] mChoice = {"Take Photo", "Choose from Gallery"};
    private Drawable[] mImageArray;
    private String[] mFunctionNameArray;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerViewNavigation;
    private CircleImageView mCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initView();
        initData();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarDrawerLayout);
        mToolbar.setTitle(TITLE_TOOLBAR_DRAWER_LAYOUT);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mRecyclerViewNavigation = findViewById(R.id.recyclerViewDrawer);
        mCircleImageView = findViewById(R.id.imgAvatar);
    }

    private void initData() {
        mImageArray = new Drawable[]{getResources().getDrawable(R.drawable.ic_inbox),
                getResources().getDrawable(R.drawable.ic_setting),
                getResources().getDrawable(R.drawable.ic_spam),
                getResources().getDrawable(R.drawable.ic_trash)};
        mFunctionNameArray = getResources().getStringArray(R.array.function_name_array);

        List<OptionData> listData = createOptionData();

        DrawerAdapter drawerAdapter = new DrawerAdapter(listData, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewNavigation.setLayoutManager(mLayoutManager);
        mRecyclerViewNavigation.setAdapter(drawerAdapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.label_open, R.string.label_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private List<OptionData> createOptionData() {
        List<OptionData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            OptionData data = new OptionData(mImageArray[i], mFunctionNameArray[i]);
            list.add(data);
        }
        return list;
    }

    @Override
    public void onItemClick(String nameFunction) {
        Toast.makeText(getApplicationContext(), "" + nameFunction, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageAvatarClick(final CircleImageView circleImageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TITLE_OPTION_DIALOG)
                .setItems(mChoice, (dialogInterface, i) -> {
                    if (mChoice[i].equals(mChoice[0])) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            mCircleImageView = circleImageView;
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    } else if (mChoice[i].equals(mChoice[1])) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mCircleImageView.setImageBitmap(imageBitmap);
        }
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                assert imageUri != null;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mCircleImageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "onActivityResult: " + e);
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
