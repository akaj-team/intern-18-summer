package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class DrawerLayoutActivity extends AppCompatActivity implements OnClickListener {
    private RecyclerView mrecyclerView;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    private List<ItemMenu> mListItemMenu;
    private List<UserHeader> mListUserHeader;
    private final static int GALLERY_REQUEST = 101;
    public final static int CAMERA_REQUEST = 102;
    private final int PERMISSION_CODE_STORAGE = 1;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        mrecyclerView = findViewById(R.id.recyclerViewDrawerlayout);
        initListMenuItem();
        initRecyclerView();

    }

    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(mListItemMenu, mListUserHeader, this);
        mrecyclerView.setLayoutManager(layoutManager);
        mrecyclerView.setAdapter(mDrawerLayoutAdapter);
    }

    public void initListMenuItem() {
        mListItemMenu = new ArrayList<>();
        mListUserHeader = new ArrayList<>();
        mListUserHeader.add(new UserHeader(R.drawable.hugh));
        mListItemMenu.add(new ItemMenu("inbox", R.drawable.ic_inbox));
        mListItemMenu.add(new ItemMenu("outbox", R.drawable.ic_outbox));
        mListItemMenu.add(new ItemMenu("trash", R.drawable.ic_trash));
        mListItemMenu.add(new ItemMenu("spam", R.drawable.ic_spam));
        mListItemMenu = CreateListItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST: {
                    Bitmap bitmap;
                    if (Objects.requireNonNull(data).getExtras() != null) {
                        bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
                        mListUserHeader.get(0).setUri(Uri.parse(path));
                        mDrawerLayoutAdapter.notifyDataSetChanged();
                    }
                    break;
                }
                case GALLERY_REQUEST: {

                    if (data != null) {
                        uri = data.getData();
                        mListUserHeader.get(0).setUri(uri);
                        mDrawerLayoutAdapter.notifyDataSetChanged();
                    }
                    break;
                }

            }
        }
    }

    public void showdialog() {
        // khoi tao dialog
        mDialog = new Dialog(this);
        //set layout dialog
        mDialog.setContentView(R.layout.dialog_choose_photo);
        Button btnOpenCamera = mDialog.findViewById(R.id.btnOpenCamera);
        Button btnOpenGallery = mDialog.findViewById(R.id.btnChooseFromGallery);
        btnOpenCamera.setOnClickListener(view -> {

            openCamera();
            mDialog.cancel();
        });
        btnOpenGallery.setOnClickListener(view -> {
            openLibraryImage();
            mDialog.cancel();
        });
        //hien thi Dioloag;
        mDialog.show();
    }

    private void openCamera() {

        Intent getPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getPhotoIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(getPhotoIntent, CAMERA_REQUEST);
        } else {
            throw new RuntimeException();
        }
    }

    public void openLibraryImage() {
        Intent takePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        takePhotoIntent.setType("image/*");
        startActivityForResult(takePhotoIntent, GALLERY_REQUEST);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permisstion needed")
                    .setMessage("This permission needed because of insert camera")
                    .setPositiveButton("ok", (dialogInterface, i) -> ActivityCompat.requestPermissions(DrawerLayoutActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE))
                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Pemission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Pemission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*click pick photo to profile*/
    @Override
    public void onGetAvatarClick() {
        if (ContextCompat.checkSelfPermission(DrawerLayoutActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(DrawerLayoutActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();
            // Permission is not granted
        } else {
            requestStoragePermission();

        }
        showdialog();
    }

    /*click random item*/
    @Override
    public void onToastClickItem(int position) {
        Toast.makeText(this, mListItemMenu.get(position).getTitleItem(), Toast.LENGTH_SHORT).show();
    }

    public List<ItemMenu> CreateListItem() {
        List<ItemMenu> listItem = new ArrayList<>(), listClone;
        listClone = new ArrayList<>(mListItemMenu);
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int position = random.nextInt(listClone.size());
            listItem.add(listClone.remove(position));
        }
        return listItem;
    }
}
