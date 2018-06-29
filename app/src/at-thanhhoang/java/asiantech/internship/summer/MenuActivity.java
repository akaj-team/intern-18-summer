package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.exercise_fragment_activity.FragmentActivity;
import asiantech.internship.summer.exercise_recycler_view.TimelineActivity;
import asiantech.internship.summer.exercise_view_view_group.MainTwoActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TITLE_MENU = "MENU";

    private Button mBtnEx1;
    private Button mBtnEx2;
    private Button mBtnEx3;
    private Button mBtnEx4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        addListener();
    }

    private void addListener() {
        mBtnEx1.setOnClickListener(this);
        mBtnEx2.setOnClickListener(this);
        mBtnEx3.setOnClickListener(this);
        mBtnEx4.setOnClickListener(this);
    }

    private void initView() {
        Toolbar toolbarMenu = findViewById(R.id.toolbarMenu);
        toolbarMenu.setTitle(TITLE_MENU);
        setSupportActionBar(toolbarMenu);

        mBtnEx1 = findViewById(R.id.btnViewViewGroup);
        mBtnEx2 = findViewById(R.id.btnIntent);
        mBtnEx3 = findViewById(R.id.btnActivityFragment);
        mBtnEx4 = findViewById(R.id.btnRecycleView);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id){
            case R.id.btnViewViewGroup:
                intent = new Intent(MenuActivity.this,MainTwoActivity.class);
                startActivity(intent);
                break;

            case R.id.btnIntent:
                break;

            case R.id.btnActivityFragment:
                intent = new Intent(MenuActivity.this,FragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRecycleView:
                intent = new Intent(MenuActivity.this,TimelineActivity.class);
                startActivity(intent);
                break;
        }
    }
}
