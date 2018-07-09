package asiantech.internship.summer.menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.DrawerLayoutActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnRecyclerview = findViewById(R.id.btnRecyclerview);
        btnRecyclerview.setOnClickListener(view -> {
            Intent intentRecyclerview = new Intent(MenuActivity.this, RecyclerViewActivity.class);
            startActivity(intentRecyclerview);
        });

        Button btnDrawerlayout = findViewById(R.id.btnDrawerlayout);
        btnDrawerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DrawerLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
