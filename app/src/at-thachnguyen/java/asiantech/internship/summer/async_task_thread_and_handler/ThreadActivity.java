package asiantech.internship.summer.async_task_thread_and_handler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

@SuppressLint("SetTextI18n")
public class ThreadActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button mBtnDownload;
    private List<Bitmap> mListImages;
    private ImageAdapter mImageAdapter;
    private RecyclerView mRecyclerViewImage;
    private final String mImagePath1 = "https://cdn.pixabay.com/photo/2017/04/05/11/56/image-in-the-image-2204798_960_720.jpg";
    private final String mImagePath2 = "https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg";
    private final String mImagePath3 = "https://searchengineland.com/figz/wp-content/seloads/2016/03/google-photos-images-camera-ss-1920-800x450.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        init();
        mToolbar.setSubtitle(getResources().getString(R.string.thread));
        mToolbar.inflateMenu(R.menu.main_menu);
        mBtnDownload.setText(getResources().getString(R.string.thread));
        mListImages = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mListImages, this);
        mRecyclerViewImage.setAdapter(mImageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewImage.setLayoutManager(layoutManager);
        mToolbar.setOnMenuItemClickListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.mnuAsyncTask:
                    intent = new Intent(ThreadActivity.this, AsyncTaskActivity.class);
                    startActivity(intent);
                case R.id.mnuThread:
                    break;
                case R.id.mnuHandler:
                    intent = new Intent(ThreadActivity.this, HandlerActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        });

        mBtnDownload.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setTitle(getResources().getString(R.string.thread));
            progressDialog.setMessage(getResources().getString(R.string.message_download));
            progressDialog.show();
            new Thread(() -> {
                final Bitmap bitmap1 = DownloadBitmapImage.downloadBitmap(mImagePath1);
                final Bitmap bitmap2 = DownloadBitmapImage.downloadBitmap(mImagePath2);
                final Bitmap bitmap3 = DownloadBitmapImage.downloadBitmap(mImagePath3);
                mBtnDownload.post(() -> {
                    mListImages.clear();
                    mListImages.add(bitmap1);
                    mListImages.add(bitmap2);
                    mListImages.add(bitmap3);
                    mImageAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                });
            }).start();
        });
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnDownload = findViewById(R.id.btnDownload);
        mRecyclerViewImage = findViewById(R.id.recyclerViewImage);
    }
}
