package asiantech.internship.summer.asynctask_thread_handler.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import asiantech.internship.summer.R;

public class AsyncTaskFragment extends Fragment {
    private static final String TEXT_DOWNLOAD_BUTTON = "Download Images AsyncTask";
    private static final String TAG = AsyncTaskFragment.class.getSimpleName();
    private static final String TITLE_DIALOG = "AsyncTask";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL_1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYDG8QyfSJrCJC5A3TvY5KS2JAnjoYftrSxGsXpbz8K60SdeXi";
    private static final String IMAGE_URL_2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTg8QuVsondoncaHdDudDZkKe-9M6oJsuggpMqa2P5ucV7FYiHsdA";

    private Button mBtnDownload;
    private ImageView mImgResultA;
    private ImageView mImgResultB;
    private ImageView mImgResultC;
    private ImageView mImgResultD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_images, container, false);

        initView(view);
        addListener();
        return view;
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mImgResultA = view.findViewById(R.id.imgResultA);
        mImgResultB = view.findViewById(R.id.imgResultB);
        mImgResultC = view.findViewById(R.id.imgResultC);
        mImgResultD = view.findViewById(R.id.imgResultD);

        mBtnDownload.setText(TEXT_DOWNLOAD_BUTTON);
    }

    private void addListener() {
        mBtnDownload.setOnClickListener(view -> {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(IMAGE_URL_1, IMAGE_URL_2);
        });
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadTask extends AsyncTask<String, Integer, ArrayList<Bitmap>> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(TITLE_DIALOG);
            mProgressDialog.setMessage(MESSAGE_DIALOG);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {
            int count = strings.length;
            HttpURLConnection connection;
            ArrayList<Bitmap> bitmaps = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                try {
                    URL currentURL = new URL(strings[i]);
                    connection = (HttpURLConnection) currentURL.openConnection();
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "InterruptedException: " + e);
                    }

                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                    bitmaps.add(bmp);

                    publishProgress((int) (((i + 1) / (float) count) * 100));
                    if (isCancelled()) {
                        break;
                    }

                } catch (IOException e) {
                    Log.d(TAG, "doInBackground: " + e);
                }
            }
            return bitmaps;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            mProgressDialog.dismiss();
            mImgResultA.setImageBitmap(bitmaps.get(0));
            mImgResultB.setImageBitmap(bitmaps.get(0));
            mImgResultC.setImageBitmap(bitmaps.get(1));
            mImgResultD.setImageBitmap(bitmaps.get(1));
        }
    }
}
