package asiantech.internship.summer.unittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

public class UnitTestActivity extends AppCompatActivity {
    private TextView mTvNotificationBug;
    private EditText mEdtPassword;
    private EditText mEdtUserName;
    private Button mBtnCheckWithUnitTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        initViews();
        addListener();
    }

    private void initViews() {
        mTvNotificationBug = findViewById(R.id.tvNotificationBug);
        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtPassword = findViewById(R.id.edtPassword);
        mBtnCheckWithUnitTest = findViewById(R.id.btnCheckWithUnitTest);
    }

    private void addListener() {
        mBtnCheckWithUnitTest.setOnClickListener(view -> {
            String username = mEdtUserName.getText().toString().trim();
            String password = mEdtPassword.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                User user = new User(username, password);
                String Result;
                Result = getResources().getString(ValidatorInit.validateUserName(username))
                        + "\n" + getResources().getString(ValidatorInit.validatePassword(password))
                        + "\n" + getResources().getString(ValidatorInit.checkUserName(user));
                mTvNotificationBug.setText(Result);
            }
        });
    }
}
