package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import java.util.Objects;
import asiantech.internship.summer.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPassword = findViewById(R.id.tvPassword);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
          tvEmail.setText(bundle.getString(LogInFragment.DATA_RECEIVE_EMAIL));
            tvPassword.setText(bundle.getString(LogInFragment.DATA_RECEIVE_PASSWORD));
        }
    }
}
