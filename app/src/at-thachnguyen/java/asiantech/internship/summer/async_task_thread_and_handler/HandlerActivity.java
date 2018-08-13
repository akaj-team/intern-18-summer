package asiantech.internship.summer.async_task_thread_and_handler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import asiantech.internship.summer.R;

@SuppressLint("SetTextI18n")
public class HandlerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button mBtnDownload;
    private List<Bitmap> mListImages;
    private ImageAdapter mImageAdapter;
    private RecyclerView mRecyclerViewImage;
    private final String mImagePath1 = "https://cdn.pixabay.com/photo/2017/04/05/11/56/image-in-the-image-2204798_960_720.jpg";
    private final String mImagePath2 = "https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg";
    private final String mImagePath3 = "https://searchengineland.com/figz/wp-content/seloads/2016/03/google-photos-images-camera-ss-1920-800x450.jpg";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        init();
        mToolbar.setSubtitle(getResources().getString(R.string.handler));
        mToolbar.inflateMenu(R.menu.main_menu);
        mBtnDownload.setText(getResources().getString(R.string.handler));
        mListImages = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mListImages, this);
        mRecyclerViewImage.setAdapter(mImageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewImage.setLayoutManager(layoutManager);
        mToolbar.setOnMenuItemClickListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.mnuAsyncTask:
                    intent = new Intent(HandlerActivity.this, AsyncTaskActivity.class);
                    startActivity(intent);
                case R.id.mnuThread:
                    intent = new Intent(HandlerActivity.this, ThreadActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mnuHandler:
                    break;
            }
            return false;
        });
        mBtnDownload.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setTitle(getResources().getString(R.string.handler));
            progressDialog.setMessage(getResources().getString(R.string.message_download));
            progressDialog.show();
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Bitmap bitmaps[] = (Bitmap[]) msg.obj;
                    mListImages.clear();
                    mListImages.addAll(Arrays.asList(bitmaps));
                    mImageAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            };
            new Thread(() -> {
                Message message = handler.obtainMessage();
                Bitmap[] bitmaps = new Bitmap[3];
                bitmaps[0] = DownloadBitmapImage.downloadBitmap(mImagePath1);
                bitmaps[1] = DownloadBitmapImage.downloadBitmap(mImagePath2);
                bitmaps[2] = DownloadBitmapImage.downloadBitmap(mImagePath3);
                message.obj = bitmaps;
                handler.sendMessage(message);
            }).start();
        });
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnDownload = findViewById(R.id.btnDownload);
        mRecyclerViewImage = findViewById(R.id.recyclerViewImage);
    }
}
