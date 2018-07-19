package asiantech.internship.summer.rest_api;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.rest_api.adapter.RestAPIAdapter;
import asiantech.internship.summer.rest_api.models.ImageInfo;
import asiantech.internship.summer.rest_api.remote.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ACCESS_TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    private static final String TITLE_TOOLBAR = "REST API";
    private static final String TITLE_OPTION_DIALOG = "Option";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_LOAD_IMG = 2;

    private final CharSequence[] mChoiceOption = {"Take Photo", "Choose from Gallery"};

    private Button mBtnLoadAPI;
    private Button mBtnUploadAPI;
    private RecyclerView mImagesRecyclerView;

    private RestAPIAdapter mRestAPIAdapter;
    private List<String> mUrlList;

    private String mPathImage;

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarRestApi);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);

        mBtnLoadAPI = findViewById(R.id.btnLoadImageApi);
        mBtnUploadAPI = findViewById(R.id.btnUploadToApi);
        mImagesRecyclerView = findViewById(R.id.recyclerViewImages);

        mUrlList = new ArrayList<>();
    }

    private void addListener() {
        mBtnLoadAPI.setOnClickListener(this);
        mBtnUploadAPI.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadImageApi:
                downloadImages();
                break;
            case R.id.btnUploadToApi:
                showOptionDialog();
                break;
        }
    }

    private void showOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TITLE_OPTION_DIALOG)
                .setItems(mChoiceOption, (dialogInterface, i) -> {
                    if (mChoiceOption[i].equals(mChoiceOption[0])) {
                        if (isStoragePermissionGranted()) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    } else if (mChoiceOption[i].equals(mChoiceOption[1])) {
                        if (isStoragePermissionGranted()) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        }
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void downloadImages() {
        ApiUtils.getServiceDownload().downloadImages(ACCESS_TOKEN).enqueue(new Callback<List<ImageInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageInfo>> successCall, @NonNull Response<List<ImageInfo>> response) {
                mUrlList.clear();
                for (ImageInfo images : Objects.requireNonNull(response.body())) {
                    mUrlList.add(images.getUrl());
                }

                mRestAPIAdapter = new RestAPIAdapter(getApplicationContext(), mUrlList, position ->
                        Toast.makeText(getApplicationContext(), "position " + position, Toast.LENGTH_SHORT).show());

                mImagesRecyclerView.setHasFixedSize(true);
                mImagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mImagesRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                mImagesRecyclerView.setAdapter(mRestAPIAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageInfo>> failedCall, @NonNull Throwable exception) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) Objects.requireNonNull(extras).get("data");
            if (photo != null) {
                Uri photoUri = getUriImageCamera(getApplicationContext(), photo);
                mPathImage = getPathImageCamera(photoUri);
                uploadImage();
            }
        }

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            final Uri uriImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(getApplicationContext(), uriImage, filePathColumn, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            mPathImage = cursor.getString(column_index);
            cursor.close();
            uploadImage();
        }
    }

    private Uri getUriImageCamera(Context context, Bitmap photo) {
        if (isExternalStorageWritable()) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
            return Uri.parse(path);
        }
        return null;
    }

    private String getPathImageCamera(Uri photoUri) {
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(photoUri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
        return null;
    }

    private void uploadImage() {
        File file = new File(mPathImage);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        Call<Void> upload = ApiUtils.getServiceUpload().uploadImages(ACCESS_TOKEN, fileToUpload);
        upload.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toast.makeText(RestAPIActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(RestAPIActivity.this, "Upload fail!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
