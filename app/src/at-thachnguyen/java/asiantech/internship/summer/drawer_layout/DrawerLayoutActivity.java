package asiantech.internship.summer.drawer_layout;

import android.app.Application;
import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        RecyclerView recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        mBitmaps = new ArrayList<>();
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.img_avt));
        menuItems.add(new MenuItem("Inbox", R.drawable.ic_inbox));
        menuItems.add(new MenuItem("Delete", R.drawable.ic_delete));
        menuItems.add(new MenuItem("Outbox", R.drawable.ic_outbox));
        menuItems.add(new MenuItem("Spam", R.drawable.ic_spam));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(manager);
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(menuItems, this, mBitmaps);
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
                    Bitmap bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                            uri);
                    mBitmaps.add(0, bp);
                    mBitmaps.remove(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mDrawerLayoutAdapter.notifyDataSetChanged();
    }

    private void createListItems(){
        final Random rnd = new Random();
        final String str = "img_" + rnd.nextInt(4);
        String resName="img_" + rnd.nextInt(4);

        for (int i = 0; i < 10 ; i++) {
            getResources().getDrawable(getResources().getIdentifier(resName, "drawable",
                    getApplicationInfo().packageName));
        }
    }
}
