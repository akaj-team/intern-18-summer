package asiantech.internship.summer.exercise_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class ResultActivity extends AppCompatActivity{

    private TextView mTvEmailResult, mTvPasswordResult;
    private Toolbar mToolbarResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();

        mToolbarResult.setTitle("Result Activity");
        setSupportActionBar(mToolbarResult);

        Intent intent = getIntent();

        String email_show = intent.getStringExtra(LoginFragment.KEY_MAIL);
        String pass_show = intent.getStringExtra(LoginFragment.KEY_PASS);

        setData(email_show,pass_show);

    }

    private void setData(String email_show, String pass_show) {
        mTvEmailResult.setText(email_show);
        mTvPasswordResult.setText(pass_show);
    }

    private void initView() {
        mTvEmailResult = findViewById(R.id.tvEmailResult);
        mTvPasswordResult = findViewById(R.id.tvPasswordResult);

        mToolbarResult = findViewById(R.id.toolbarResult);
    }
}
