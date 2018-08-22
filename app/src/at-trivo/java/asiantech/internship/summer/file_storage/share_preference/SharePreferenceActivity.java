package asiantech.internship.summer.file_storage.share_preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class SharePreferenceActivity extends AppCompatActivity {

    private final String PASSWORD_KEY = "password key";
    private final String EMAIL_KEY = "email key";
    private CheckBox mChkRemember;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);
        setUpViewComponents();
        setSavedInformation();
        setUpLoginAction();
    }

    private void setUpViewComponents() {
        mChkRemember = findViewById(R.id.chkRemember);
        mEdtPassword = findViewById(R.id.edtPassword);
        mEdtEmail = findViewById(R.id.edtEmail);
        mTvResult = findViewById(R.id.tvResult);
    }

    private void setUpLoginAction() {
        TextView tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int resultId;
            if (mChkRemember.isChecked()) {
                String password = mEdtPassword.getText().toString();
                String email = mEdtEmail.getText().toString();
                editor.putString(PASSWORD_KEY, password);
                editor.putString(EMAIL_KEY, email);
                resultId = R.string.saved_login;
            } else {
                editor.remove(PASSWORD_KEY);
                editor.remove(EMAIL_KEY);
                resultId = R.string.unsaved_login;
            }
            editor.apply();
            mTvResult.setText(resultId);
        });
    }

    private void setSavedInformation() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(PASSWORD_KEY, "");
        String email = sharedPreferences.getString(EMAIL_KEY, "");
        mEdtPassword.setText(password);
        mEdtEmail.setText(email);
    }
}
