package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.DrawerLayoutActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnView;
    private Button mBtnActivity;
    private Button mBtnRecyclerView;
    private Button mBtnViewPager;
    private Button mBtnDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        mBtnView.setOnClickListener(this);
        mBtnActivity.setOnClickListener(this);
        mBtnRecyclerView.setOnClickListener(this);
        mBtnViewPager.setOnClickListener(this);
        mBtnDrawerLayout.setOnClickListener(this);

    }

    private void init(){
         mBtnView = findViewById(R.id.btnView);
         mBtnActivity = findViewById(R.id.btnActivity);
         mBtnRecyclerView = findViewById(R.id.btnRecyclerView);
         mBtnViewPager = findViewById(R.id.btnViewPager);
         mBtnDrawerLayout = findViewById(R.id.btnDrawerLayout);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnView:
                break;
            case R.id.btnActivity:
                break;
            case R.id.btnRecyclerView:
                intent = new Intent(MenuActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnViewPager:
                break;
            case R.id.btnDrawerLayout:
                intent = new Intent(MenuActivity.this, DrawerLayoutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
