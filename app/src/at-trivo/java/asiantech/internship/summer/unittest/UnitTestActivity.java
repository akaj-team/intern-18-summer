package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.helpers.Validator;
import asiantech.internship.summer.unittest.model.User;

public class UnitTestActivity extends AppCompatActivity {

    private TextView mTvNotification;
    private EditText mEdtUsername;
    private EditText mEdtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        initView();
    }

    private void initView() {
        mTvNotification = findViewById(R.id.tvNotification);
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
    }

    public void login(View view) {
        String username = mEdtUsername.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        mTvNotification.setText(Validator.validate(new User(username, password)));
    }
}
