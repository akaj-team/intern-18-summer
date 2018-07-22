package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.activity_and_fragment.ActivityAndFragmentActivity;
import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.view_and_view_group.ViewActivity;
import asiantech.internship.summer.viewpager.PagerActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnView;
    private Button mBtnActivity;
    private Button mBtnRecyclerView;
    private Button mBtnViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        mBtnView.setOnClickListener(this);
        mBtnActivity.setOnClickListener(this);
        mBtnRecyclerView.setOnClickListener(this);
        mBtnViewPager.setOnClickListener(this);
    }

    private void init() {
        mBtnView = findViewById(R.id.btnView);
        mBtnActivity = findViewById(R.id.btnActivity);
        mBtnRecyclerView = findViewById(R.id.btnRecyclerView);
        mBtnViewPager = findViewById(R.id.btnViewPager);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnView:
                intent = new Intent(MenuActivity.this, ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnActivity:
                intent = new Intent(MenuActivity.this, ActivityAndFragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRecyclerView:
                intent = new Intent(MenuActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnViewPager:
                intent = new Intent(MenuActivity.this, PagerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
