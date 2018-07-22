package asiantech.internship.summer.asynctask_thread_handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class LooperThread extends Thread {
    private Handler mHandler;
    private AsynctaskThreadHandlerActivity.Update mUpdate;

    LooperThread(AsynctaskThreadHandlerActivity.Update update) {
        this.mUpdate = update;
    }

    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message message) {
                CustomOject customOject = (CustomOject) message.obj;
                int count = customOject.getmUrl().length;
                HttpURLConnection connection = null;
                for (int i = 0; i < count; i++) {
                    URL[] current = customOject.getmUrl();
                    try {
                        connection = (HttpURLConnection) current[i].openConnection();
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        final Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                        final ImageView imageView = customOject.getmListImage().get(i);
                        new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(bitmap));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                    final int percent = (int) (((i + 1) / (float) count) * 100);
                    new Handler(Looper.getMainLooper()).post(() -> mUpdate.updateProgress(percent));
                }
                new Handler(Looper.getMainLooper()).post(() -> mUpdate.updateDismiss());
            }
        };
        Looper.loop();
    }

    public Handler getHandler() {
        return mHandler;
    }

    protected void onDestroy() {
        Objects.requireNonNull(Looper.myLooper()).quit();
    }
}
