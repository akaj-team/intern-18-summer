package asiantech.internship.summer.drawer_layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.model.MenuItem;

public class DrawerLayoutActivity extends AppCompatActivity {
    private ArrayList<Bitmap> mBitmaps;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    private ArrayList<MenuItem> mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        RecyclerView recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        mMenuItems = new ArrayList<>();
        createListItems();
        mBitmaps = new ArrayList<>();
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.img_avt));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(manager);
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(mMenuItems, this, mBitmaps);
        recyclerViewMenu.setAdapter(mDrawerLayoutAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == DrawerLayoutAdapter.PICK_FROM_CAMERA) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                mBitmaps.add(0, bp);
                mBitmaps.remove(1);
            } else {
                Uri uri = data.getData();
                try {
                    Bitmap bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    mBitmaps.add(0, bp);
                    mBitmaps.remove(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mDrawerLayoutAdapter.notifyDataSetChanged();
    }

    private void createListItems() {
        final Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            int rndNumber = rnd.nextInt(11);
            String resName = "ic_" + rndNumber;
            int image = getResources().getIdentifier(resName, "drawable", getApplicationInfo().packageName);
            String[] array = getResources().getStringArray(R.array.title);
            String title = array[rndNumber];
            mMenuItems.add(new MenuItem(title, image));
        }
    }
}
