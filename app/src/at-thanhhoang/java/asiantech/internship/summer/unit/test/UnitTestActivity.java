package asiantech.internship.summer.unit.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class UnitTestActivity extends AppCompatActivity {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private TextView mTvError;
    private Button mBtnLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        initView();
        addListener();
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPasswordUnitTest);
        mTvError = findViewById(R.id.tvError);
        mBtnLogin = findViewById(R.id.btnLogIn);
    }

    private void addListener() {
        mBtnLogin.setOnClickListener(v -> {
            Account account = new Account(mEdtUsername.getText().toString().trim(), mEdtPassword.getText().toString().trim());
            mTvError.setText(UtilValidate.isLogin(account));
            if (mTvError.getText().toString().equals(getResources().getString(R.string.check_login_success))) {
                mTvError.setTextColor(getResources().getColor(R.color.colorGreenLight));
            } else {
                mTvError.setTextColor(getResources().getColor(R.color.colorRed));
            }
        });
    }
}
