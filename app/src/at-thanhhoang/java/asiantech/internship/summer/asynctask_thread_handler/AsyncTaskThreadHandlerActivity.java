package asiantech.internship.summer.asynctask_thread_handler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.MenuActivity;
import asiantech.internship.summer.R;
import asiantech.internship.summer.asynctask_thread_handler.fragment.AsyncTaskFragment;
import asiantech.internship.summer.asynctask_thread_handler.fragment.HandlerFragment;
import asiantech.internship.summer.asynctask_thread_handler.fragment.ThreadFragment;

@SuppressLint("Registered")
public class AsyncTaskThreadHandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TITLE_TOOLBAR = "Download Images";

    private Button mBtnAsyncTask;
    private Button mBtnThread;
    private Button mBtnHandler;

    private AsyncTaskFragment mAsyncTaskFragment;
    private ThreadFragment mThreadFragment;
    private HandlerFragment mHandlerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask_thread_handler);

        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarThread);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);

        mBtnAsyncTask = findViewById(R.id.btnAsyncTask);
        mBtnThread = findViewById(R.id.btnThread);
        mBtnHandler = findViewById(R.id.btnHandler);

        mAsyncTaskFragment = new AsyncTaskFragment();
        mThreadFragment = new ThreadFragment();
        mHandlerFragment = new HandlerFragment();

        replaceFragment(mAsyncTaskFragment);
    }

    private void addListener() {
        mBtnAsyncTask.setOnClickListener(this);
        mBtnThread.setOnClickListener(this);
        mBtnHandler.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAsyncTask:
                replaceFragment(mAsyncTaskFragment);
                break;

            case R.id.btnThread:
                replaceFragment(mThreadFragment);
                break;

            case R.id.btnHandler:
                replaceFragment(mHandlerFragment);
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerDownload, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            startActivity(new Intent(AsyncTaskThreadHandlerActivity.this, MenuActivity.class));
        }
    }
}
