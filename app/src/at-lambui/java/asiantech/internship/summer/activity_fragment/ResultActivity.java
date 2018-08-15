package asiantech.internship.summer.activity_fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import asiantech.internship.summer.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tvDisplayEmail = findViewById(R.id.tvDisplayEmail);
        TextView tvDisplayPassword = findViewById(R.id.tvDisplayPassword);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Result");
        tvDisplayEmail.setText(getIntent().getStringExtra("email_receive"));
        tvDisplayPassword.setText(getIntent().getStringExtra("password_receive"));
    }
}
