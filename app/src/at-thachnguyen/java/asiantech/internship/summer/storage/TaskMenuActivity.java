package asiantech.internship.summer.storage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class TaskMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSharePreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_menu);
        init();
        mBtnSharePreference.setOnClickListener(this);
    }

    private void init(){
        mBtnSharePreference=findViewById(R.id.btnSharePreference);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSharePreference:
                Intent intent=new Intent(TaskMenuActivity.this, SharePreferenceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
