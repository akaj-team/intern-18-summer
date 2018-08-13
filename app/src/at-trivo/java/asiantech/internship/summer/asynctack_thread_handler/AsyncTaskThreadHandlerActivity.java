package asiantech.internship.summer.asynctack_thread_handler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.HashMap;

import asiantech.internship.summer.R;
import asiantech.internship.summer.asynctack_thread_handler.downloaders.ImageDownloaderAsyncTask;
import asiantech.internship.summer.asynctack_thread_handler.downloaders.ImageDownloaderThread;
import asiantech.internship.summer.asynctack_thread_handler.downloaders.LooperThread;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity {

    private TextView mTvType;
    private PopupMenu mPopupMenu;
    private ImageView mImgTypeChooser;
    private LinearLayout mLlShowImages;
    private HashMap<String, DownloadDelegate> mDelegateMap;
    private UpdateListener mRunOnUiThreadListener, mRunOnNonUiThreadListener;
    private LooperThread mLooperThread;
    private ProgressDialog mProgressDialog;
    private String[] mUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        mLlShowImages = findViewById(R.id.llShowImages);
        createData();
        createListeners();
        setUpActionBar();
        setUpPopupMenu();
        mImgTypeChooser.setOnClickListener(view1 -> mPopupMenu.show());
        mLooperThread = new LooperThread(getCacheDir(), new Handler(), mRunOnNonUiThreadListener);
        mLooperThread.start();
    }

    @Override
    protected void onDestroy() {
        mLooperThread.endLooper();
        super.onDestroy();
    }

    private void createData() {
        mUrls = new String[]{
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png",
                "https://cdn.pixabay.com/photo/2017/05/09/21/49/gecko-2299365_960_720.jpg",
                "https://images.pexels.com/photos/994605/pexels-photo-994605.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/230629/pexels-photo-230629.jpeg?auto=compress&cs=tinysrgb&h=350"
        };

        mDelegateMap = new HashMap<>();
        mDelegateMap.put(getResources().getString(R.string.download_async_task), this::asyncTaskDownload);
        mDelegateMap.put(getResources().getString(R.string.download_thread), this::threadDownload);
        mDelegateMap.put(getResources().getString(R.string.download_handler), this::handlerDownload);
    }

    public void createListeners() {
        mRunOnUiThreadListener = new UpdateListener() {
            @Override
            public void updateImage(Bitmap bitmap) {
                if (bitmap == null) {
                    return;
                }
                ImageView imageView = new ImageView(AsyncTaskThreadHandlerActivity.this);
                imageView.setImageBitmap(bitmap);
                mLlShowImages.addView(imageView);
            }

            @Override
            public void updateProgress(int percent) {
                mProgressDialog.setProgress(percent);
            }

            @Override
            public void updateComplete() {
                mProgressDialog.dismiss();
            }
        };

        mRunOnNonUiThreadListener = new UpdateListener() {
            @Override
            public void updateImage(Bitmap bitmap) {
                runOnUiThread(() -> {
                    if (bitmap == null) {
                        return;
                    }
                    ImageView imageView = new ImageView(AsyncTaskThreadHandlerActivity.this);
                    imageView.setImageBitmap(bitmap);
                    mLlShowImages.addView(imageView);
                });
            }

            @Override
            public void updateProgress(int percent) {
                runOnUiThread(() -> mProgressDialog.setProgress(percent));
            }

            @Override
            public void updateComplete() {
                runOnUiThread(() -> mProgressDialog.dismiss());
            }
        };
    }

    private void setUpPopupMenu() {
        mPopupMenu = new PopupMenu(AsyncTaskThreadHandlerActivity.this, mImgTypeChooser);
        mPopupMenu.inflate(R.menu.download_type);
        mPopupMenu.setOnMenuItemClickListener(menuItem -> {
            mTvType.setText(menuItem.getTitle().toString());
            return true;
        });
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.custom_action_bar_download_file);
            mTvType = actionBar.getCustomView().findViewById(R.id.tvType);
            mImgTypeChooser = actionBar.getCustomView().findViewById(R.id.imgTypeChooser);
        }
    }

    public void download(View view) {
        mDelegateMap.get(mTvType.getText().toString()).download();
    }

    private void threadDownload() {
        createProgressDialog();
        new ImageDownloaderThread(mUrls, getCacheDir(), mRunOnNonUiThreadListener).start();
    }

    private void asyncTaskDownload() {
        ImageDownloaderAsyncTask imageDownloaderAsyncTask = new ImageDownloaderAsyncTask(getCacheDir(), mRunOnUiThreadListener);
        createProgressDialog();
        imageDownloaderAsyncTask.execute(mUrls);
    }

    private void handlerDownload() {
        Message message = new Message();
        message.obj = mUrls;
        createProgressDialog();
        mLooperThread.getHandler().sendMessage(message);
    }

    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(R.string.downloading);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
        mProgressDialog.show();
    }

    interface DownloadDelegate {
        void download();
    }
}
