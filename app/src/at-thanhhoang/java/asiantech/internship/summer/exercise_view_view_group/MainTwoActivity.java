package asiantech.internship.summer.exercise_view_view_group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import asiantech.internship.summer.R;

public class MainTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private View mViewProfile1, mViewProfile2, mViewProfile3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        initView();
    }

    private void initView() {

        mViewProfile1 = findViewById(R.id.viewItemOne);
        mViewProfile2 = findViewById(R.id.viewItemTwo);
        mViewProfile3 = findViewById(R.id.viewItemThree);

        LinearLayout llProfile1 = findViewById(R.id.llProfile1);
        LinearLayout llProfile2 = findViewById(R.id.llProfile2);
        LinearLayout llProfile3 = findViewById(R.id.llProfile3);

        llProfile1.setOnClickListener(this);
        llProfile2.setOnClickListener(this);
        llProfile3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llProfile1:
                mViewProfile1.setBackgroundResource(R.color.colorPressed);
                mViewProfile2.setBackgroundResource(R.color.colorDefault);
                mViewProfile3.setBackgroundResource(R.color.colorDefault);
                break;
            case R.id.llProfile2:
                mViewProfile1.setBackgroundResource(R.color.colorDefault);
                mViewProfile2.setBackgroundResource(R.color.colorPressed);
                mViewProfile3.setBackgroundResource(R.color.colorDefault);
                break;
            case R.id.llProfile3:
                mViewProfile1.setBackgroundResource(R.color.colorDefault);
                mViewProfile2.setBackgroundResource(R.color.colorDefault);
                mViewProfile3.setBackgroundResource(R.color.colorPressed);
                break;
        }
    }
}
