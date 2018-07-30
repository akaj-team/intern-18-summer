package asiantech.internship.summer.rest_api;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import asiantech.internship.summer.rest_api.remote.Api;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private final CharSequence[] mChoiceOption = {"Take Photo", "Choose from Gallery"};

    private Button mBtnLoadAPI;
    private Button mBtnUploadAPI;
    private ProgressDialog mProgressDialog;

    private RestAPIAdapter mRestAPIAdapter;
    private List<String> mUrlList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarApi);
        toolbar.setTitle(getResources().getString(R.string.label_rest_api_toolbar_title));
        setSupportActionBar(toolbar);

        mBtnLoadAPI = findViewById(R.id.btnLoadApi);
        mBtnUploadAPI = findViewById(R.id.btnUploadApi);
        RecyclerView imagesRecyclerView = findViewById(R.id.recyclerViewImages);

        mRestAPIAdapter = new RestAPIAdapter(getApplicationContext(), mUrlList, position ->
                Toast.makeText(getApplicationContext(), "position " + position, Toast.LENGTH_SHORT).show());

        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        imagesRecyclerView.setAdapter(mRestAPIAdapter);
    }

    private void addListener() {
        mBtnLoadAPI.setOnClickListener(this);
        mBtnUploadAPI.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadApi:
                setProgressBarDialog(getResources().getString(R.string.label_api_dialog_download_title),
                        getResources().getString(R.string.label_api_dialog_download_message));
                downloadImages();
                break;
            case R.id.btnUploadApi:
                showDialogOption();
                break;
        }
    }

    private void setProgressBarDialog(String title, String message) {
        mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void showDialogOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.label_api_option))
                .setItems(mChoiceOption, (dialogInterface, i) -> {
                    if (mChoiceOption[i].equals(mChoiceOption[0])) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        } else {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                            } else {
                                openCamera();
                            }
                        }
                    } else if (mChoiceOption[i].equals(mChoiceOption[1])) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
                        } else {
                            openGallery();
                        }
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {
                        openCamera();
                    }
                } else {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "gallery permission granted", Toast.LENGTH_LONG).show();
                    openGallery();
                } else {
                    Toast.makeText(this, "gallery permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void downloadImages() {
        Api retrofitApi = new Retrofit.Builder().baseUrl(getResources().getString(R.string.url_download))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        retrofitApi.downloadImages(getResources().getString(R.string.access_token_api), 30).enqueue(new Callback<List<ImageInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageInfo>> successCall, @NonNull Response<List<ImageInfo>> response) {
                mUrlList.clear();
                for (ImageInfo images : Objects.requireNonNull(response.body())) {
                    mUrlList.add(images.getUrl());
                }
                mRestAPIAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
                Toast.makeText(getBaseContext(), "download completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageInfo>> failedCall, @NonNull Throwable exception) {
                mProgressDialog.dismiss();
                Toast.makeText(getBaseContext(), "download failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) Objects.requireNonNull(extras).get("data");
                if (photo != null) {
                    final Uri photoUri = getUriImageCamera(photo);
                    uploadImage(photoUri);
                }
            } else if (requestCode == REQUEST_GALLERY) {
                final Uri imageUri = data.getData();
                uploadImage(imageUri);
            }
        }
    }

    private String getRealPathImage(Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private Uri getUriImageCamera(Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        setProgressBarDialog(getResources().getString(R.string.label_api_dialog_upload_title), getResources().getString(R.string.label_api_dialog_upload_message));
        File file = new File(Objects.requireNonNull(getRealPathImage(imageUri)));

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        Api retrofitApi = new Retrofit.Builder().baseUrl(getResources().getString(R.string.url_upload))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        retrofitApi.uploadImages(getResources().getString(R.string.access_token_api), fileToUpload).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    mProgressDialog.dismiss();
                    Toast.makeText(RestAPIActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestAPIActivity.this, "Upload failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
