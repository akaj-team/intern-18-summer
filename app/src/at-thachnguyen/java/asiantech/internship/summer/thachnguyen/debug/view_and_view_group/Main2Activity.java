package asiantech.internship.summer.thachnguyen.debug.view_and_view_group;

        import android.graphics.Color;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.LinearLayout;

        import asiantech.internship.summer.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private View view1, view2, view3;
    private LinearLayout ll3, ll4, ll5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);

        ll4=findViewById(R.id.ll4);
        ll5=findViewById(R.id.ll5);
        ll3=findViewById(R.id.ll3);

        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        ll5.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ll3:
                view1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                view2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                view3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
            case R.id.ll4:
                view1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                view2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                view3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
             case R.id.ll5:
                 view1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                 view2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                 view3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                break;
        }
    }
}


