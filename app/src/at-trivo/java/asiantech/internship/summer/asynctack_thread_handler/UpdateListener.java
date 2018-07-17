package asiantech.internship.summer.asynctack_thread_handler;

import android.graphics.Bitmap;

public interface UpdateListener {
    void updateImage(Bitmap bitmap);
    void updateProcess(int percent);
    void updateComplete();
}
