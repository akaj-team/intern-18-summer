package asiantech.internship.summer.thachnguyen.debug.view_and_view_group;

        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.LinearLayout;
        import asiantech.internship.summer.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private View mViewSelector1, mViewSelector2, mViewSelector3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mViewSelector1=findViewById(R.id.view1);
        mViewSelector2=findViewById(R.id.view2);
        mViewSelector3=findViewById(R.id.view3);

        LinearLayout ll4 = findViewById(R.id.ll4);
        LinearLayout ll5 = findViewById(R.id.ll5);
        LinearLayout ll3 = findViewById(R.id.ll3);

        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        ll5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ll3:
                mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
            case R.id.ll4:
                mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
             case R.id.ll5:
                 mViewSelector1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                 mViewSelector2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                 mViewSelector3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                break;
        }
    }
}
