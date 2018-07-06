package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.exercise_fragment_activity.FragmentActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnFragmentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        addListener();
    }

    private void addListener() {
        mBtnFragmentActivity.setOnClickListener(this);
    }

    private void initView() {
        mBtnFragmentActivity = findViewById(R.id.btnActivityFragment);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnActivityFragment:
                intent = new Intent(MenuActivity.this, FragmentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
