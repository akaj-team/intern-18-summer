package asiantech.internship.summer.restful;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.restful.helpers.IntentHelper;
import asiantech.internship.summer.restful.helpers.Uri2PathHelper;
import asiantech.internship.summer.restful.model.Image;
import asiantech.internship.summer.restful.model.QueryImage;
import asiantech.internship.summer.restful.recyclerview.ImageAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestfulActivity extends AppCompatActivity {
    private final int ASK_PERMISSION_REQUEST_CODE = 1656;
    private final int UPLOAD_IMAGE_REQUEST_CODE = 3;
    private final int FIRST_PAGE = 1;

    private ImagesAPI mImagesAPI;
    private Callback<List<QueryImage>> mGetCallback;
    private Callback<Image> mUploadCallback;

    private boolean mIsLoading;
    private int mCurrentPage;
    private int mLastQueryImageNumber;
    private List<Image> mImages = new ArrayList<>();

    private Intent mUploadImageIntent;
    private ImageAdapter mImageAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private ProgressBar mUploadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful);
        initView();
        createCallbacks();
        setUpApi();
        setUpRecyclerView();
        refresh();
    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mProgressBar = findViewById(R.id.progressBar);
        mUploadProgressBar = findViewById(R.id.progressBarUpload);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void createCallbacks() {
        mGetCallback = new Callback<List<QueryImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<QueryImage>> call, @NonNull Response<List<QueryImage>> response) {
                hasResultUpdate();
                if (response.isSuccessful()) {
                    List<QueryImage> addImages = response.body();
                    if (addImages != null) {
                        mImages.addAll(addImages);
                        mImageAdapter.notifyDataSetChanged();
                        mLastQueryImageNumber = addImages.size();
                        mCurrentPage++;
                        mIsLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<QueryImage>> call, @NonNull Throwable t) {
                hasResultUpdate();
            }

            private void hasResultUpdate() {
                if (mCurrentPage == FIRST_PAGE) {
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        };
        mUploadCallback = new Callback<Image>() {
            @Override
            public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                mUploadProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mImages.add(0, response.body());
                    mImageAdapter.notifyItemInserted(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                mUploadProgressBar.setVisibility(View.GONE);
            }
        };
    }

    private void setUpApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit getImagesRetrofit = new Retrofit.Builder()
                .baseUrl(ImagesAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mImagesAPI = getImagesRetrofit.create(ImagesAPI.class);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        mImageAdapter = new ImageAdapter(mImages, this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mImageAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLastQueryImageNumber == ImagesAPI.PER_PAGE && !mIsLoading &&
                        mLayoutManager.getItemCount() == mLayoutManager.findLastVisibleItemPosition() + 1) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    getImages();
                }
            }
        });
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mImages.clear();
        mCurrentPage = FIRST_PAGE;
        getImages();
    }

    private void getImages() {
        mIsLoading = true;
        mImagesAPI.getImages(ImagesAPI.TOKEN, mCurrentPage, ImagesAPI.PER_PAGE).enqueue(mGetCallback);
    }

    public void uploadImage(View view) {
        mUploadImageIntent = IntentHelper.getPickImageIntent(this);
        if (mUploadImageIntent != null) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.CAMERA)) {
                startActivityForResult(mUploadImageIntent, UPLOAD_IMAGE_REQUEST_CODE);
            }
        }
    }

    private boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission}, ASK_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(mUploadImageIntent, UPLOAD_IMAGE_REQUEST_CODE);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permission, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == UPLOAD_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = imageReturnedIntent.getData();
            File file = new File(Uri2PathHelper.getRealPathFromURI(this.getContentResolver(), selectedImageUri));
            MultipartBody.Part image = MultipartBody.Part.createFormData(
                    "imagedata",
                    file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), ImagesAPI.TOKEN);
            mUploadProgressBar.setVisibility(View.VISIBLE);
            mImagesAPI.uploadImage(ImagesAPI.UPLOAD_URL, token, image).enqueue(mUploadCallback);
        }
    }
}
