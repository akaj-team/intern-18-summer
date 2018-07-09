package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.storage.TaskMenuActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnView;
    private Button mBtnActivity;
    private Button mBtnRecyclerView;
    private Button mBtnStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        mBtnView.setOnClickListener(this);
        mBtnActivity.setOnClickListener(this);
        mBtnRecyclerView.setOnClickListener(this);
        mBtnStorage.setOnClickListener(this);
    }

    private void init(){

        mBtnView = findViewById(R.id.btnView);
        mBtnActivity = findViewById(R.id.btnActivity);
        mBtnRecyclerView = findViewById(R.id.btnRecyclerView);
        mBtnStorage = findViewById(R.id.btnStorage);
    }

    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.btnView:
                break;
            case R.id.btnActivity:
                break;
            case R.id.btnRecyclerView:
                mIntent = new Intent(MenuActivity.this, RecyclerViewActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnStorage:
                mIntent = new Intent(MenuActivity.this, TaskMenuActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
