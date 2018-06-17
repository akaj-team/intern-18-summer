package asiantech.internship.summer;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvProfile1;
    TextView tvProfile2;
    TextView tvProfile3;
    LinearLayout   llProfile1;
    LinearLayout   llProfile2;
    LinearLayout   llProfile3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        llProfile1.setOnClickListener(this);
        llProfile2.setOnClickListener(this);
        llProfile3.setOnClickListener(this);



    }
    public void anhxa()
    {
        tvProfile1 = findViewById(R.id.tvProfile1);
        tvProfile2 = findViewById(R.id.tvProfile2);
        tvProfile3 = findViewById(R.id.tvProfile3);
        llProfile1 = findViewById(R.id.llProfile1);
        llProfile2 = findViewById(R.id.llProfile2);
        llProfile3 = findViewById(R.id.llProfile3);

    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.llProfile1:
                tvProfile1.setBackgroundColor(Color.CYAN);
                tvProfile2.setBackgroundColor(Color.WHITE);
                tvProfile3.setBackgroundColor(Color.WHITE);
                break;
        }
        switch (v.getId()) {
            case R.id.llProfile2:
                tvProfile1.setBackgroundColor(Color.WHITE);
                tvProfile2.setBackgroundColor(Color.CYAN);
                tvProfile3.setBackgroundColor(Color.WHITE);
                break;
        }
        switch (v.getId()) {
            case R.id.llProfile3:
                tvProfile1.setBackgroundColor(Color.WHITE);
                tvProfile2.setBackgroundColor(Color.WHITE);
                tvProfile3.setBackgroundColor(Color.CYAN);
                break;
        }

    }


}


