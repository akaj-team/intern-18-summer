package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.exercise_fragment_activity.FragmentActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnIntent;
    private Button mBtnFragmentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        addListener();
    }

    private void addListener() {
        mBtnIntent.setOnClickListener(this);
        mBtnFragmentActivity.setOnClickListener(this);
    }

    private void initView() {
        mBtnIntent = findViewById(R.id.btnIntent);
        mBtnFragmentActivity = findViewById(R.id.btnActivityFragment);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
            case R.id.btnIntent:
                break;

            case R.id.btnActivityFragment:
                intent = new Intent(MenuActivity.this, FragmentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
