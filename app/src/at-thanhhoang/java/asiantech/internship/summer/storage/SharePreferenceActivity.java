package asiantech.internship.summer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class SharePreferenceActivity extends AppCompatActivity {
    private static final String TOOLBAR_TITLE = "Login";
    private static final String KEY_SHARE_PREFERENCE = "share preference";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STATE = "state";
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private CheckBox mChkRemember;
    private Button mBtnLogin;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);

        mSharedPref = SharePreferenceActivity.this.getSharedPreferences(KEY_SHARE_PREFERENCE, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();

        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarPreference);
        toolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(toolbar);

        mEdtUsername = findViewById(R.id.edtUsernamePreference);
        mEdtPassword = findViewById(R.id.edtPasswordPreference);
        mChkRemember = findViewById(R.id.chkRemember);
        mBtnLogin = findViewById(R.id.btnLogin);
    }

    private void addListener() {
        mBtnLogin.setOnClickListener(view -> {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
            boolean isCheckState = mChkRemember.isChecked();

            mEditor.putString(KEY_USERNAME, username);
            mEditor.putString(KEY_PASSWORD, password);
            mEditor.putBoolean(KEY_STATE, isCheckState);
            mEditor.apply();

            Toast.makeText(getApplicationContext(), "hello: " + mSharedPref.getBoolean(KEY_STATE, false), Toast.LENGTH_SHORT).show();

        });
    }

    private void checkStateStart() {
        Toast.makeText(getApplicationContext(), "checkStateStart", Toast.LENGTH_SHORT).show();
        boolean status = mSharedPref.getBoolean(KEY_STATE, false);
        Log.d("aaa", "checkStateStart: " + status);
        if (status) {
            mEdtUsername.setText(mSharedPref.getString(KEY_USERNAME, null));
            mEdtPassword.setText(mSharedPref.getString(KEY_PASSWORD, null));
            mChkRemember.setChecked(true);
        } else {
            mEdtUsername.setText(mSharedPref.getString("", null));
            mEdtPassword.setText(mSharedPref.getString("", null));
            mChkRemember.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPref != null) {
            checkStateStart();
        }
    }
}
