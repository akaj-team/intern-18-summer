package asiantech.internship.summer.asynctack_thread_handler.downloaders;

import android.graphics.Bitmap;

import java.io.File;

import asiantech.internship.summer.asynctack_thread_handler.UpdateListener;

public class ImageDownloaderThread extends Thread {

    private String[] mUrls;
    private UpdateListener mListener;
    private Downloader mDownloader;
    private int mDownloadPosition;

    public ImageDownloaderThread(String[] url, File cacheDir, UpdateListener listener) {
        this.mUrls = url;
        this.mListener = listener;
        mDownloader = new Downloader(cacheDir) {
            @Override
            void updateProgress(int addPercent) {
                int basePercent = 100 * mDownloadPosition / mUrls.length;
                mListener.updateProgress(addPercent / mUrls.length + basePercent);
            }
        };
    }

    public void run() {
        for (int i = 0; i < mUrls.length; i++) {
            mDownloadPosition = i;
            Bitmap image = mDownloader.download(mUrls[i]);
            mListener.updateImage(image);
        }
        mListener.updateComplete();
    }
}
