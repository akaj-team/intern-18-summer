package asiantech.internship.summer.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.ViewAndViewGruopActivity;
import asiantech.internship.summer.R;
import asiantech.internship.summer.asynctask_thread_handler.AsynctaskThreadHandlerActivity;
import asiantech.internship.summer.viewpager.PagerActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnViewAndViewGroup = findViewById(R.id.btnViewAndViewGroup);
        btnViewAndViewGroup.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, ViewAndViewGruopActivity.class);
            startActivity(intent);
        });
        Button btnViewpager = findViewById(R.id.btnViewPager);
        btnViewpager.setOnClickListener(view -> {
            Intent intentViewpager = new Intent(MenuActivity.this, PagerActivity.class);
            startActivity(intentViewpager);
        });
        Button btnAsynctaskThreadHandler = findViewById(R.id.btnAsynctaskThreadHandler);
        btnAsynctaskThreadHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAsynctaskThreadHandler = new Intent(MenuActivity.this, AsynctaskThreadHandlerActivity.class);
                startActivity(intentAsynctaskThreadHandler);
            }
        });

    }
}
