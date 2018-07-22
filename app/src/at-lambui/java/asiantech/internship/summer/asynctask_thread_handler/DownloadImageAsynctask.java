package asiantech.internship.summer.asynctask_thread_handler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadImageAsynctask extends AsyncTask<URL, Integer, List<Bitmap>> {
    private List<ImageView> mListImage;
    private ProgressDialog mProgressDialog;
    /* backgroud*/

    DownloadImageAsynctask(List<ImageView> mListImage, ProgressDialog progressDialog) {
        this.mListImage = mListImage;
        this.mProgressDialog = progressDialog;
    }

    @Override
    protected List<Bitmap> doInBackground(URL... urls) {
        int count = urls.length;
        HttpURLConnection connection = null;
        List<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            URL current = urls[i];
            try {
                connection = (HttpURLConnection) current.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                bitmaps.add(bitmap);

                publishProgress((int) (((i + 1) / (float) count) * 100));
                if (isCancelled()) {
                    break;
                }
                bufferedInputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return bitmaps;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setTitle("AsyncTask");
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(List<Bitmap> result) {
        super.onPostExecute(result);
        for (int i = 0; i < 4; i++) {
            mListImage.get(i).setImageBitmap(result.get(i));
        }
        mProgressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
