package asiantech.internship.summer.async_task_thread_and_handler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

@SuppressLint("SetTextI18n")
public class AsyncTaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button mBtnDownload;
    private List<Bitmap> mListImages;
    private ImageAdapter mImageAdapter;
    private RecyclerView mRecyclerViewImage;
    private final String mImagePath1 = "https://cdn.pixabay.com/photo/2017/04/05/11/56/image-in-the-image-2204798_960_720.jpg";
    private final String mImagePath2 = "https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg";
    private final String mImagePath3 = "https://searchengineland.com/figz/wp-content/seloads/2016/03/google-photos-images-camera-ss-1920-800x450.jpg";
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        init();
        mToolbar.setSubtitle(getResources().getString(R.string.async_task));
        mToolbar.inflateMenu(R.menu.main_menu);
        mBtnDownload.setText(getResources().getString(R.string.async_task));
        mListImages = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mListImages, this);
        mRecyclerViewImage.setAdapter(mImageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewImage.setLayoutManager(layoutManager);
        mProgress = new ProgressDialog(this);
        mToolbar.setOnMenuItemClickListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.mnuAsyncTask:
                    break;
                case R.id.mnuThread:
                    intent = new Intent(AsyncTaskActivity.this, ThreadActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mnuHandler:
                    intent = new Intent(AsyncTaskActivity.this, HandlerActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        });
        mBtnDownload.setOnClickListener(v -> {
            mProgress.setTitle(getResources().getString(R.string.async_task));
            mProgress.setMessage(getResources().getString(R.string.message_download));
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
            mProgress.setProgress(0);
            mProgress.setMax(100);
            DownloadImage downloadImage = new DownloadImage();
            downloadImage.execute(mImagePath1, mImagePath2, mImagePath3);
        });
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnDownload = findViewById(R.id.btnDownload);
        mRecyclerViewImage = findViewById(R.id.recyclerViewImage);
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadImage extends AsyncTask<String, Integer, ArrayList<Bitmap>> {
        private ArrayList<Bitmap> mImages;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.show();
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {
            mImages = new ArrayList<>();
            Bitmap map;
            for (String url : params) {
                map = downloadImage(url);
                mImages.add(map);
            }
            return mImages;
        }

        private Bitmap downloadImage(String urlString) {
            int count;
            Bitmap bitmap;
            URL url;
            BufferedOutputStream outputStream;
            InputStream inputStream;
            try {
                url = new URL(urlString);
                URLConnection connection = url.openConnection();
                int fileLength = connection.getContentLength();
                ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                inputStream = new BufferedInputStream(url.openStream());
                outputStream = new BufferedOutputStream(dataStream);
                byte data[] = new byte[512];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress((int) ((total * 100) / fileLength));
                    outputStream.write(data, 0, count);
                }
                outputStream.flush();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                byte[] bytes = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                return null;
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgress.setIndeterminate(false);
            mProgress.setMax(100);
            mProgress.setProgress((mImages.size() + 1) * 100 / 3);
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            mProgress.dismiss();
            mListImages.clear();
            mListImages.addAll(bitmaps);
            mImageAdapter.notifyDataSetChanged();
        }
    }
}
