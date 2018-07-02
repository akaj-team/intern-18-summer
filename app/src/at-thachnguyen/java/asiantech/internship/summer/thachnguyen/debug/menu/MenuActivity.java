package asiantech.internship.summer.thachnguyen.debug.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.view_and_view_group.Main2Activity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnEx1 = findViewById(R.id.btnEx1);
        btnEx1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.btnEx1:
                mIntent = new Intent(MenuActivity.this, Main2Activity.class);
                startActivity(mIntent);
                break;
        }
    }
}
