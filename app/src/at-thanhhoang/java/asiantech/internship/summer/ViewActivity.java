package asiantech.internship.summer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private View mViewProfile1;
    private View mViewProfile2;
    private View mViewProfile3;
    private LinearLayout mLlProfile1;
    private LinearLayout mLlProfile2;
    private LinearLayout mLlProfile3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initView();
        addListener();
    }

    private void initView() {
        mViewProfile1 = findViewById(R.id.viewProfile1);
        mViewProfile2 = findViewById(R.id.viewProfile2);
        mViewProfile3 = findViewById(R.id.viewProfile3);

        mLlProfile1 = findViewById(R.id.llProfile1);
        mLlProfile2 = findViewById(R.id.llProfile2);
        mLlProfile3 = findViewById(R.id.llProfile3);
    }

    private void addListener() {
        mLlProfile1.setOnClickListener(this);
        mLlProfile2.setOnClickListener(this);
        mLlProfile3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llProfile1:
                mViewProfile1.setBackgroundResource(R.color.colorOrangeDark);
                mViewProfile2.setBackgroundResource(R.color.colorYellowPale);
                mViewProfile3.setBackgroundResource(R.color.colorYellowPale);
                break;
            case R.id.llProfile2:
                mViewProfile1.setBackgroundResource(R.color.colorYellowPale);
                mViewProfile2.setBackgroundResource(R.color.colorOrangeDark);
                mViewProfile3.setBackgroundResource(R.color.colorYellowPale);
                break;
            case R.id.llProfile3:
                mViewProfile1.setBackgroundResource(R.color.colorYellowPale);
                mViewProfile2.setBackgroundResource(R.color.colorYellowPale);
                mViewProfile3.setBackgroundResource(R.color.colorOrangeDark);
                break;
        }
    }
}
