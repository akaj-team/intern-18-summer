package asiantech.internship.summer.storage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class SharePreferenceActivity extends AppCompatActivity {
    private Button mBtnLogin;
    private CheckBox mChkRemember;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private final static String MY_PREF="my_preference";
    private final static String MAIL="mail";
    private final static String PASSWORD="password";
    private final static String REMEMBER="remember";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);
        init();
        SharedPreferences preferences=getSharedPreferences(MY_PREF, MODE_PRIVATE);
        String mail=preferences.getString(MAIL,"");
        String password=preferences.getString(PASSWORD,"");
        Boolean remember =preferences.getBoolean(REMEMBER, false);
        mEdtEmail.setText(mail);
        mEdtPassword.setText(password);
        mChkRemember.setChecked(remember);
        mBtnLogin.setOnClickListener(v -> {
            store();
            Toast.makeText(this, "You stored status login", Toast.LENGTH_SHORT).show();
        });
    }

    private void store(){
        String mail=mEdtEmail.getText().toString();
        String password=mEdtPassword.getText().toString();
        SharedPreferences preferences=getSharedPreferences(MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if (mChkRemember.isChecked()) {
            editor.putString(MAIL, mail);
            editor.putString(PASSWORD, password);
            editor.putBoolean(REMEMBER, mChkRemember.isChecked());
        }
        else {
            editor.clear();
        }
        editor.apply();
    }

    private void init(){
        mBtnLogin=findViewById(R.id.btnLogin);
        mChkRemember=findViewById(R.id.chkRemember);
        mEdtEmail=findViewById(R.id.edtEmail);
        mEdtPassword=findViewById(R.id.edtPassword);
    }
}
