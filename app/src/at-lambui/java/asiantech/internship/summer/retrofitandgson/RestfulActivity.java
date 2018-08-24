package asiantech.internship.summer.retrofitandgson;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.retrofitandgson.adapter.ListImageAdapter;
import asiantech.internship.summer.retrofitandgson.api.ImageAPI;
import asiantech.internship.summer.retrofitandgson.model.CustomImage;
import asiantech.internship.summer.retrofitandgson.model.Image;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestfulActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String GET_IMAGE_URL = "https://api.gyazo.com/api/";
    private static final String POST_IMAGE_URL = "https://upload.gyazo.com/api/";
    private final static String FILE_STORAGE_CAMERA = "temp.png";
    private static final String TAG = RestfulActivity.class.getSimpleName();
    private final static int GALLERY_REQUEST = 101;
    public final static int CAMERA_REQUEST = 102;

    private ListImageAdapter mListImageAdapter;
    private List<Image> mListImage = new ArrayList<>();
    private Button mBtnGetImage;
    private Button mBtnUploadImage;
    private RecyclerView mRecyclerViewImage;
    private ProgressDialog mProgressDialog;

    private final int PERMISSION_CODE_STORAGE = 1;
    private Dialog mDialog;
    private static final int PER_PAGE = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful);
        initView();
        initRecyclerView();
        setListeners();
    }

    private void setListeners() {
        mBtnGetImage.setOnClickListener(this);
        mBtnUploadImage.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecyclerViewImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RestfulActivity.this, 2);
        mRecyclerViewImage.setLayoutManager(gridLayoutManager);
        mListImageAdapter = new ListImageAdapter(mListImage, RestfulActivity.this);
        mRecyclerViewImage.setAdapter(mListImageAdapter);

    }

    private void initView() {
        mBtnGetImage = findViewById(R.id.btnGetImage);
        mBtnUploadImage = findViewById(R.id.btnUploadImage);
        mRecyclerViewImage = findViewById(R.id.recycleViewRestful);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_dialog))
                    .setMessage(getResources().getString(R.string.message_dialog))
                    .setPositiveButton(getResources().getString(R.string.ok), (dialog, i) -> ActivityCompat.requestPermissions(RestfulActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE))
                    .setNegativeButton(getResources().getString(R.string.cancel), (dialog, i) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_CODE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.message_failure), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetImage: {
                getListImage();
                break;
            }
            case R.id.btnUploadImage: {
                pickImage();
                break;
            }
        }
    }

    //Load image to server
    private void getListImage() {
        addProgressbarDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GET_IMAGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImageAPI getImangeAPI = retrofit.create(ImageAPI.class);
        Call<List<Image>> call = getImangeAPI.getData(ImageAPI.ACCESS_TOKEN, PER_PAGE);
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                mListImage.clear();
                mListImage.addAll(Objects.requireNonNull(response.body()));
                mListImageAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestfulActivity.this, "Get image OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadImage(File filePath) {
        addProgressbarDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POST_IMAGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImageAPI imageAPI = retrofit.create(ImageAPI.class);
        //create file
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);
        RequestBody accesstokenBody = RequestBody.create(MediaType.parse("text/plain"), ImageAPI.ACCESS_TOKEN);
        MultipartBody.Part imageUpload = MultipartBody.Part.createFormData("imagedata", filePath.getName(), requestBody);
        Call<CustomImage> callUploadAPI = imageAPI.postData(accesstokenBody, imageUpload);
        callUploadAPI.enqueue(new Callback<CustomImage>() {
            @Override
            public void onResponse(@NonNull Call<CustomImage> call, @NonNull Response<CustomImage> response) {
                if (response.isSuccessful()) {
                    mProgressDialog.dismiss();
                    Toast.makeText(RestfulActivity.this, "Image upload successfull", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RestfulActivity.this, "Upload fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomImage> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestfulActivity.this, "Upload fail , Not connect", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDialog() {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_choose_photo);
        Button btnOpenCamera = mDialog.findViewById(R.id.btnOpenCamera);
        Button btnOpenGallery = mDialog.findViewById(R.id.btnChooseFromGallery);
        btnOpenCamera.setOnClickListener(v -> {
            openCamera();
            mDialog.cancel();
        });
        btnOpenGallery.setOnClickListener(v -> {
            openLibraryImage();
            mDialog.cancel();
        });
        mDialog.show();
    }

    public void openCamera() {
        Intent getPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getPhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getPhotoIntent, CAMERA_REQUEST);
        }
    }

    public void openLibraryImage() {
        Intent takePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        takePhotoIntent.setType("image/*");
        takePhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        takePhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(takePhotoIntent, GALLERY_REQUEST);
    }

    private void pickImage() {
        if ((ContextCompat.checkSelfPermission(RestfulActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(RestfulActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(RestfulActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();
            // Permission is not granted
        } else {
            requestStoragePermission();
        }
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST: {
                    Bitmap bitmap;
                    if (data.getExtras() != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        String remoteStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        OutputStream outStream;
                        // String temp = null;
                        File file = new File(remoteStorageDirectory, FILE_STORAGE_CAMERA);
                        if (file.exists()) {
                            if (!file.delete()) {
                                return;
                            }
                            file = new File(remoteStorageDirectory, FILE_STORAGE_CAMERA);
                        }
                        try {
                            outStream = new FileOutputStream(file);
                            Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.PNG, 100, outStream);
                            outStream.flush();
                            outStream.close();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                        uploadImage(file);
                    }
                    break;
                }
                case GALLERY_REQUEST: {
                    if (data != null) {
                        uri = data.getData();
                        if (uri != null) {
                            File file = new File(getPath(uri));
                            uploadImage(file);
                        }
                    }
                }
            }
        }
    }

    private void addProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getResources().getString(R.string.message_progress));
        mProgressDialog.show();
    }

    public String getPath(Uri uri) {
        String filePath = "/";
        String wholeID = DocumentsContract.getDocumentId(uri);
        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);
        int columnIndex = 0;
        if (cursor != null) {
            columnIndex = cursor.getColumnIndex(column[0]);
        }
        if (cursor != null && cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        Objects.requireNonNull(cursor).close();
        return filePath;
    }
}
