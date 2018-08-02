package asiantech.internship.summer.view_and_view_group;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import asiantech.internship.summer.R;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {
    private View mViewSelector1;
    private View mViewSelector2;
    private View mViewSelector3;
    private LinearLayout llItem1;
    private LinearLayout llItem2;
    private LinearLayout llItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        init();
        llItem1.setOnClickListener(this);
        llItem2.setOnClickListener(this);
        llItem3.setOnClickListener(this);
    }

    private void init(){
        mViewSelector1 = findViewById(R.id.viewSelector1);
        mViewSelector2 = findViewById(R.id.viewSelector2);
        mViewSelector3 = findViewById(R.id.viewSelector3);
        llItem1 = findViewById(R.id.llItem1);
        llItem2 = findViewById(R.id.llItem2);
        llItem3 = findViewById(R.id.llItem3);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llItem1:
                mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
            case R.id.llItem2:
                mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
            case R.id.llItem3:
                mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                break;
        }
    }
}
