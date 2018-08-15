package asiantech.internship.summer.asynctack_thread_handler.downloaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class Downloader {

    private File mCacheDir;

    Downloader(File cacheDir) {
        mCacheDir = cacheDir;
    }

    public Bitmap download(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            File savedFile = saveImageInFile(connection);
            connection.disconnect();
            return getImageFromFile(savedFile);
        } catch (IOException e) {
            Log.e("TTT", e.getMessage());
        }
        return null;
    }

    private Bitmap getImageFromFile(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }

    private File saveImageInFile(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        File savedFile = new File(mCacheDir, "temp.png");
        FileOutputStream outputStream = new FileOutputStream(savedFile, false);

        int fileLength = connection.getContentLength();
        byte[] buffer = new byte[4096];
        int total = 0, count;

        updateProgress(0);
        while ((count = inputStream.read(buffer)) > 0) {
            total += count;
            outputStream.write(buffer, 0, count);
            if (fileLength > 0) {
                updateProgress(total * 100 / fileLength);
            }
        }
        updateProgress(100);
        outputStream.close();
        inputStream.close();
        return savedFile;
    }

    abstract void updateProgress(int percent);
}
