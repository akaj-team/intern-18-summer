package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnEx1 = findViewById(R.id.btnEx1);
        Button btnEx3 = findViewById(R.id.btnEx3);
        Button btnEx4 = findViewById(R.id.btnEx4);
        btnEx1.setOnClickListener(this);
        btnEx3.setOnClickListener(this);
        btnEx4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.btnEx1:
                break;
            case R.id.btnEx3:
                break;
            case R.id.btnEx4:
                mIntent = new Intent(MenuActivity.this, RecyclerViewActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
