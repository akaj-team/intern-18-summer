package asiantech.internship.summer.rest_api;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.rest_api.adapter.ImageAdapter;
import asiantech.internship.summer.rest_api.models.Images;
import asiantech.internship.summer.rest_api.models.ServerResponse;
import asiantech.internship.summer.rest_api.remote.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TITLE_TOOLBAR = "REST API";
    private static final String TITLE_OPTION_DIALOG = "Option";
    private static final String ACCESS_TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_LOAD_IMG = 2;
    private static final String TAG = RestAPIActivity.class.getSimpleName();
    private final CharSequence[] mChoice = {"Take Photo", "Choose from Gallery"};
    private Button mBtnLoadAPI;
    private Button mBtnUploadAPI;
    private RecyclerView mRecyclerViewImages;
    private ImageAdapter mImageAdapter;
    private List<String> mListUrl;

    private String mPathImage;

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
        mRecyclerViewImages = findViewById(R.id.recyclerViewImages);

        mListUrl = new ArrayList<>();
    }

    private void addListener() {
        mBtnLoadAPI.setOnClickListener(this);
        mBtnUploadAPI.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadImageApi:
                loadImages();
                break;
            case R.id.btnUploadToApi:
                showOptionDialog();
                break;
        }
    }

    private void showOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TITLE_OPTION_DIALOG)
                .setItems(mChoice, (dialogInterface, i) -> {
                    if (mChoice[i].equals(mChoice[0])) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    } else if (mChoice[i].equals(mChoice[1])) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void loadImages() {
        Log.d(TAG, "loadImages: 1");
        ApiUtils.getServiceDownload().downloadImages(ACCESS_TOKEN).enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(@NonNull Call<List<Images>> successCall, @NonNull Response<List<Images>> response) {
                mListUrl.clear();
                Log.d(TAG, "loadImages: 2");
                for (Images images : Objects.requireNonNull(response.body())) {
                    mListUrl.add(images.getUrl());
                }
                mImageAdapter = new ImageAdapter(getApplicationContext(), mListUrl, position ->
                        Toast.makeText(getApplicationContext(), "position " + position, Toast.LENGTH_SHORT).show());
                mRecyclerViewImages.setHasFixedSize(true);
                mRecyclerViewImages.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

                mRecyclerViewImages.setAdapter(mImageAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Images>> failedCall, @NonNull Throwable exception) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }

        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                CursorLoader loader = new CursorLoader(getApplicationContext(),imageUri, filePathColumn, null, null, null);
                Cursor cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                mPathImage =cursor.getString(column_index);
                cursor.close();


//                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
//                assert cursor != null;
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                mPathImage = cursor.getString(columnIndex);
//                cursor.close();

                uploadImage();
            }
        } catch (Exception e) {
            Log.d(TAG, "onActivityResult: " + e);
        }
    }

    public void uploadImage() {
        Toast.makeText(this, "upload", Toast.LENGTH_SHORT).show();
        File file = new File(mPathImage);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        Call<ServerResponse> upload = ApiUtils.getServiceUpload().uploadImages(ACCESS_TOKEN, requestBody);
        upload.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                Toast.makeText(RestAPIActivity.this, "done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
