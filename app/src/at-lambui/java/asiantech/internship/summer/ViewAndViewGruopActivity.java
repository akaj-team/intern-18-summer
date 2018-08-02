package asiantech.internship.summer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ViewAndViewGruopActivity extends AppCompatActivity implements View.OnClickListener {
    private View mViewUser1;
    private View mViewUser2;
    private View mViewUser3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewandviewgroup);
        initView();
    }

    public void initView() {
        mViewUser1 = findViewById(R.id.tvProfile1);
        mViewUser2 = findViewById(R.id.tvProfile2);
        mViewUser3 = findViewById(R.id.tvProfile3);
        LinearLayout llProfile1 = findViewById(R.id.llProfile1);
        LinearLayout llProfile2 = findViewById(R.id.llProfile2);
        LinearLayout llProfile3 = findViewById(R.id.llProfile3);
        llProfile1.setOnClickListener(this);
        llProfile2.setOnClickListener(this);
        llProfile3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llProfile1:
                mViewUser1.setBackgroundColor(Color.CYAN);
                mViewUser2.setBackgroundColor(Color.WHITE);
                mViewUser3.setBackgroundColor(Color.WHITE);
                break;
            case R.id.llProfile2:
                mViewUser1.setBackgroundColor(Color.WHITE);
                mViewUser2.setBackgroundColor(Color.CYAN);
                mViewUser3.setBackgroundColor(Color.WHITE);
                break;
            case R.id.llProfile3:
                mViewUser1.setBackgroundColor(Color.WHITE);
                mViewUser2.setBackgroundColor(Color.WHITE);
                mViewUser3.setBackgroundColor(Color.CYAN);
                break;
        }
    }
}
