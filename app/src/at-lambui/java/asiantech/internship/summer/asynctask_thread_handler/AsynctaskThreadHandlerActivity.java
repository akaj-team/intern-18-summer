package asiantech.internship.summer.asynctask_thread_handler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class AsynctaskThreadHandlerActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    public LooperThread looperThread;
    private static String save;
    private static final String ASYNCTASK = "asyctask";
    private static final String THREAD = "thread";
    private static final String HANDLER = "handler";

    public final static URL IMAGE_URL = stringToURL("http://www.freeimageslive.com/galleries/transtech/informationtechnology/pics/beige_keyboard.jpg");
    public final static URL IMAGE_URL_2 = stringToURL("http://www.freeimageslive.com/galleries/transtech/informationtechnology/pics/computer_blank_screen.jpg");
    public final static URL IMAGE_URL_3 = stringToURL("http://www.freeimageslive.com/galleries/transtech/informationtechnology/pics/computer_memory_dimm.jpg");
    public final static URL IMAGE_URL_4 = stringToURL("http://www.freeimageslive.com/galleries/transtech/informationtechnology/pics/computer_memory.jpg");

    URL[] urls = new URL[]{
            IMAGE_URL, IMAGE_URL_2, IMAGE_URL_3, IMAGE_URL_4
    };

    List<ImageView> list = new ArrayList<>();

    @SuppressLint("FindViewByIdCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask_thread_handler);
        Button btnDowload = findViewById(R.id.btnDowload);

        //tui quat nhu nay lun,
        list.add(findViewById(R.id.imgDowloadOne));
        list.add(findViewById(R.id.imgDowloadTwo));
        list.add(findViewById(R.id.imgDowloadThree));
        list.add(findViewById(R.id.imgDowloadFour));
        /////
        btnDowload.setOnClickListener(view -> {
            if(save != null){
                switch (save) {
                    case ASYNCTASK: {
                        downloadImageAsyncTask();
                        break;
                    }
                    case THREAD: {
                        downloadImageThread();
                        break;
                    }
                    case HANDLER: {
                        downloadImageHandler();
                        break;
                    }
                }
            }
        });
        looperThread = new LooperThread(new Update() {
            @Override
            public void updateProgress(int percent) {
                mProgressDialog.setProgress(percent);
            }

            @Override
            public void updateDismiss() {
                mProgressDialog.dismiss();
            }
        });
        looperThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.asyctask:
                save = ASYNCTASK;
                updateUI();
                return true;
            case R.id.thread:
                save = THREAD;
                updateUI();
                return true;
            case R.id.handler:
                save = HANDLER;
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static URL stringToURL(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadImageAsyncTask() {

        //mProgressDialog.setTitle("AsyncTask");
        addProgressbarDialog();
        DownloadImageAsynctask taskAsyctask = new DownloadImageAsynctask(list, mProgressDialog);
        taskAsyctask.execute(IMAGE_URL, IMAGE_URL_2, IMAGE_URL_3, IMAGE_URL_4);
    }

    public void downloadImageThread() {
        // mProgressDialog.setTitle("Thread");
        addProgressbarDialog();
        DownloadImageThread taskThread = new DownloadImageThread(this, urls, list, mProgressDialog);
        taskThread.start();
    }

    public void downloadImageHandler() {
        // mProgressDialog.setTitle("Handler");
        addProgressbarDialog();
        Message message = new Message();
        message.obj = new CustomOject(urls, list);
        looperThread.getHandler().sendMessage(message);
    }

    public void addProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("Please wait, we are doing your image files");
        mProgressDialog.setMax(100);
        mProgressDialog.show();
    }

    public void updateUI(){
      for(int i = 0; i < 4; i++){
          list.get(i).setImageResource(R.color.colorWhite);
      }
    }

    @Override
    protected void onDestroy() {
        looperThread.onDestroy();
        super.onDestroy();
    }

    interface Update {
        void updateProgress(int percent);
        void updateDismiss();
    }
}
