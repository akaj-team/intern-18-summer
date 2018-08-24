package asiantech.internship.summer.unit_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class UnitTestActivity extends AppCompatActivity {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        initView();
        mEdtUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mEdtUsername.setBackgroundResource(R.drawable.border_edit_text_focus);
            } else {
                mEdtUsername.setBackgroundResource(R.drawable.border_edit_text_blur);
            }
        });
        mEdtPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mEdtPassword.setBackgroundResource(R.drawable.border_edit_text_focus);
            } else {
                mEdtPassword.setBackgroundResource(R.drawable.border_edit_text_blur);
            }
        });

        mBtnLogin.setOnClickListener(v -> {
            if (mEdtUsername.getText().toString().trim().isEmpty() || mEdtPassword.getText().toString().trim().isEmpty()) {
                mTvResult.setText(getResources().getString(R.string.error));
                mTvResult.setBackgroundResource(R.drawable.bg_radius_view_warning);
            } else {
                Account account = new Account(mEdtUsername.getText().toString().trim(), mEdtPassword.getText().toString().trim());
                mTvResult.setText(UtilValidate.resultLogin(account.getUsername(), account.getPassword()));
                if (UtilValidate.resultLogin(account.getUsername(), account.getPassword()).equals(UtilValidate.SUCCESS)) {
                    mTvResult.setTextColor(getResources().getColor(R.color.colorGreen));
                    mTvResult.setBackgroundResource(R.drawable.bg_radius_view_success);
                } else {
                    mTvResult.setBackgroundResource(R.drawable.bg_radius_view_warning);
                }
            }
        });
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mTvResult = findViewById(R.id.tvResult);
    }
}
