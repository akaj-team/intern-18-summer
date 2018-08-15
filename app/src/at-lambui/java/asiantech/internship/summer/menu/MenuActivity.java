package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.retrofitandgson.RestfulActivity;
import asiantech.internship.summer.activity_fragment.HomeActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.ViewAndViewGruopActivity;
import asiantech.internship.summer.viewpager.PagerActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button mBtnRecyclerview;
    Button mBtnExerciseFragment;
    Button mBtnViewAndViewGroup;
    Button mBtnViewpager;
    Button mBtnRestful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initViews();
        setListeners();
    }

    private void initViews() {
        mBtnViewAndViewGroup = findViewById(R.id.btnViewAndViewGroup);
        mBtnExerciseFragment = findViewById(R.id.btnExerciseFragment);
        mBtnRecyclerview = findViewById(R.id.btnRecyclerview);
        mBtnViewpager = findViewById(R.id.btnViewPager);
        mBtnRestful = findViewById(R.id.btnRestful);
    }
    private void setListeners(){
        mBtnViewAndViewGroup.setOnClickListener(this);
        mBtnExerciseFragment.setOnClickListener(this);
        mBtnRecyclerview.setOnClickListener(this);
        mBtnViewpager.setOnClickListener(this);
        mBtnRestful.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnViewAndViewGroup:
                intent = new Intent(MenuActivity.this, ViewAndViewGruopActivity.class);
                startActivity(intent);
                break;
            case R.id.btnExerciseFragment:
                intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRecyclerview:
                intent = new Intent(MenuActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnViewPager:
                intent = new Intent(MenuActivity.this, PagerActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRestful:
                intent = new Intent(MenuActivity.this, RestfulActivity.class);
                startActivity(intent);
                break;
        }
    }
}
