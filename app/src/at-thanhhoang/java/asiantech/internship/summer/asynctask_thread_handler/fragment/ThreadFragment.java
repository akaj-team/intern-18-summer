package asiantech.internship.summer.asynctask_thread_handler.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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

public class ThreadFragment extends Fragment {
    private static final String TEXT_DOWNLOAD_BUTTON = "Download Images Thread";
    private static final String TAG = ThreadFragment.class.getSimpleName();
    private static final String TITLE_DIALOG = "Thread";
    private static final String MESSAGE_DIALOG = "Please wait, we are downloading your image files...";
    private static final String IMAGE_URL_1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYDG8QyfSJrCJC5A3TvY5KS2JAnjoYftrSxGsXpbz8K60SdeXi";
    private static final String IMAGE_URL_2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTg8QuVsondoncaHdDudDZkKe-9M6oJsuggpMqa2P5ucV7FYiHsdA";

    private Button mBtnDownload;
    private ImageView mImgResultA;
    private ImageView mImgResultB;
    private ImageView mImgResultC;
    private ImageView mImgResultD;

    private ProgressDialog mDialog;
    private ArrayList<Bitmap> mArrayBitmap;
    private Handler mHandler;
    private Thread mDownloadThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_images, container, false);
        mArrayBitmap = new ArrayList<>();
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
        mHandler = new Handler();

        mBtnDownload.setOnClickListener(view -> {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setTitle(TITLE_DIALOG);
            mDialog.setMessage(MESSAGE_DIALOG);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setMax(100);
            mDialog.setProgress(0);
            mDialog.setCancelable(true);
            mDialog.show();

            mDownloadThread = new MyThread();
            mDownloadThread.start();
        });
    }

    @Override
    public void onDestroy() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        super.onDestroy();
    }

    private ArrayList<Bitmap> downloadBitmap(String... urls) {
        int count = urls.length;
        HttpURLConnection connection;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            try {
                URL currentURL = new URL(urls[i]);
                connection = (HttpURLConnection) currentURL.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                bitmaps.add(bmp);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException: " + e);
                }

                setProgressDialogValues((int) (((i + 1) / (float) count) * 100));

                inputStream.close();
            } catch (IOException e) {
                Log.d(TAG, "downloadBitmap: " + e);
            }
        }
        return bitmaps;
    }

    public void setProgressDialogValues(int values) {
        mDialog.setProgress(values);
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            mArrayBitmap = (downloadBitmap(IMAGE_URL_1,IMAGE_URL_2));
            mHandler.post(new MyRunnable());
        }
    }

    public class MyRunnable implements Runnable {
        public void run() {
            mImgResultA.setImageBitmap(mArrayBitmap.get(0));
            mImgResultB.setImageBitmap(mArrayBitmap.get(0));
            mImgResultC.setImageBitmap(mArrayBitmap.get(1));
            mImgResultD.setImageBitmap(mArrayBitmap.get(1));
            mDialog.dismiss();
        }
    }
}
