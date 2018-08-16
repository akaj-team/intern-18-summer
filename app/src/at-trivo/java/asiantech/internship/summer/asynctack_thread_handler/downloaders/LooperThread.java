package asiantech.internship.summer.asynctack_thread_handler.downloaders;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;

import asiantech.internship.summer.asynctack_thread_handler.UpdateListener;

public class LooperThread extends Thread {
    private Handler mHandler;
    private Downloader mDownloader;
    private Handler mSendBackHandler;
    private UpdateListener mUpdateListener;
    private int mFileNumber;
    private Looper mLooper;
    private int mDownloadPosition;

    public LooperThread(File cacheDir, Handler handler, UpdateListener updateListener) {
        mSendBackHandler = handler;
        mUpdateListener = updateListener;
        mDownloader = new Downloader(cacheDir) {
            @Override
            void updateProgress(int addPercent) {
                mSendBackHandler.post(() -> {
                    int basePercent = 100 * mDownloadPosition / mFileNumber;
                    mUpdateListener.updateProgress(addPercent / mFileNumber + basePercent);
                });
            }
        };
    }

    public void run() {
        Looper.prepare();
        mLooper = Looper.myLooper();
        mHandler = new Handler(mLooper) {
            public void handleMessage(Message message) {
                String[] mUrls = (String[]) message.obj;
                mFileNumber = mUrls.length;
                for (int i = 0; i < mUrls.length; i++) {
                    mDownloadPosition = i;
                    Bitmap image = mDownloader.download(mUrls[i]);
                    mSendBackHandler.post(() -> mUpdateListener.updateImage(image));
                }
                mSendBackHandler.post(() -> mUpdateListener.updateComplete());
            }
        };
        Looper.loop();
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void endLooper() {
        mLooper.quit();
    }
}
