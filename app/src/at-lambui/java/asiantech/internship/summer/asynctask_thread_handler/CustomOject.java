package asiantech.internship.summer.asynctask_thread_handler;

import android.widget.ImageView;

import java.net.URL;
import java.util.List;

public class CustomOject {
    private URL[] mUrl;
    private List<ImageView> mListImage;
    CustomOject(URL[] url, List<ImageView> list) {
        this.mUrl = url;
        this.mListImage = list;
    }
    public URL[] getmUrl() {
        return mUrl;
    }

    public List<ImageView> getmListImage() {
        return mListImage;
    }
}
