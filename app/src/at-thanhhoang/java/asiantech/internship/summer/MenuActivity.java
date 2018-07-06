package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.drawerlayout.DrawerActivity;
import asiantech.internship.summer.timeline.TimelineActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnExRecyclerView;
    private Button mBtnExDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        addListener();
    }

    private void addListener() {
        mBtnExRecyclerView.setOnClickListener(this);
        mBtnExDrawerLayout.setOnClickListener(this);
    }

    private void initView() {
        mBtnExRecyclerView = findViewById(R.id.btnRecycleView);
        mBtnExDrawerLayout = findViewById(R.id.btnDrawerLayout);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnRecycleView:
                intent = new Intent(MenuActivity.this, TimelineActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDrawerLayout:
                intent = new Intent(MenuActivity.this, DrawerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
