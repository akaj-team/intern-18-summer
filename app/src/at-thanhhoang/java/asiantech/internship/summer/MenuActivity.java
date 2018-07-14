package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.storage.FileStorageActivity;
import asiantech.internship.summer.timeline.TimelineActivity;
import asiantech.internship.summer.viewpager.ViewPagerActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnViewViewGroup;
    private Button mBtnExRecyclerView;
    private Button mBtnViewPager;
    private Button mBtnFileStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        addListener();
    }

    private void addListener() {
        mBtnViewViewGroup.setOnClickListener(this);
        mBtnExRecyclerView.setOnClickListener(this);
        mBtnViewPager.setOnClickListener(this);
        mBtnFileStorage.setOnClickListener(this);
    }

    private void initView() {
        mBtnViewViewGroup = findViewById(R.id.btnView);
        mBtnExRecyclerView = findViewById(R.id.btnRecycleView);
        mBtnViewPager = findViewById(R.id.btnViewPager);
        mBtnFileStorage = findViewById(R.id.btnFileStorage);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnView:
                intent = new Intent(MenuActivity.this, ViewActivity.class);
                startActivity(intent);
                break;

            case R.id.btnRecycleView:
                intent = new Intent(MenuActivity.this, TimelineActivity.class);
                startActivity(intent);
                break;

            case R.id.btnViewPager:
                intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                break;

            case R.id.btnFileStorage:
                intent = new Intent(MenuActivity.this, FileStorageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
