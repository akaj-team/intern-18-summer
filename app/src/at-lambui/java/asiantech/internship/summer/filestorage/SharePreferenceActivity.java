package asiantech.internship.summer.filestorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class SharePreferenceActivity extends AppCompatActivity {
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private CheckBox mChkRemember;
    private Button mbtnSignIn;
    private SharedPreferences mSharedPreferences;
    private static final String KEYSAVE = "DATALOGIN";
    private static final String KEY_SAVE_ACCOUNT = "account";
    private static final String KEY_SAVE_PASSWORD = "password";
    private static final String KEY_SAVE_CHECK = "checked";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();

        mSharedPreferences = getSharedPreferences(KEYSAVE, MODE_PRIVATE);
        //get data from shareprences
        mEdtEmail.setText(mSharedPreferences.getString(KEY_SAVE_ACCOUNT, ""));
        mEdtPassword.setText(mSharedPreferences.getString(KEY_SAVE_PASSWORD, ""));
        mChkRemember.setChecked(mSharedPreferences.getBoolean(KEY_SAVE_CHECK, false));
        mbtnSignIn.setOnClickListener(v -> {
            String email = mEdtEmail.getText().toString().trim();
            String password = mEdtPassword.getText().toString().trim();
            if (email.equals("duclam08@") && password.equals("1234")) {
                Toast.makeText(SharePreferenceActivity.this, "Sign In Seccessfull", Toast.LENGTH_SHORT).show();
                if (mChkRemember.isChecked()) {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(KEY_SAVE_ACCOUNT, email);
                    editor.putString(KEY_SAVE_PASSWORD, password);
                    editor.putBoolean(KEY_SAVE_CHECK, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.remove(KEY_SAVE_ACCOUNT);
                    editor.remove(KEY_SAVE_PASSWORD);
                    editor.remove(KEY_SAVE_CHECK);
                    editor.apply();
                }

            } else {
                Toast.makeText(SharePreferenceActivity.this, "Sign In Fail !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initView() {
        mEdtEmail = findViewById(R.id.edtEmail);
        mEdtPassword = findViewById(R.id.edtPassword);
        mChkRemember = findViewById(R.id.chkRemember);
        mbtnSignIn = findViewById(R.id.btnLogin);
    }
}
