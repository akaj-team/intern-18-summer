package asiantech.internship.summer.asynctask_thread_handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadImageThread extends Thread {
    private Activity mActivity;
    private URL[] mUrls;
    private List<ImageView> mListImage;
    private ProgressDialog mProgressDialog;

    DownloadImageThread(Activity activity, URL[] mUrls, List<ImageView> mListImage, ProgressDialog mProgressDialog) {
        this.mActivity = activity;
        this.mUrls = mUrls;
        this.mListImage = mListImage;
        this.mProgressDialog = mProgressDialog;
    }

    public void run() {
        int count = mUrls.length;
        HttpURLConnection connection = null;
        for (int i = 0; i < count; i++) {
            URL current = mUrls[i];
            try {
                connection = (HttpURLConnection) current.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                final Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                final ImageView imageView = mListImage.get(i);
                mActivity.runOnUiThread(() -> imageView.setImageBitmap(bitmap));

            } catch (IOException e) {
                Log.e("AAA",e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            mProgressDialog.setProgress((int) (((i + 1) / (float) count) * 100));
        }
        mProgressDialog.dismiss();
    }
}
