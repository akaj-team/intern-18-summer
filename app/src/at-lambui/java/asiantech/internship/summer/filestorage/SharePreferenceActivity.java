package asiantech.internship.summer.filestorage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class SharePreferenceActivity extends AppCompatActivity {
    private EditText mEdtEmail;
    private EditText mEdtPasswork;
    private CheckBox mChkRemember;
    private Button   mbtnSignIn;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        String KEYSAVE = "DATALOGIN";
        mSharedPreferences = getSharedPreferences(KEYSAVE,MODE_PRIVATE);

        //get data from shareprences
        mEdtEmail.setText(mSharedPreferences.getString("account",""));
        mEdtPasswork.setText(mSharedPreferences.getString("password",""));
        mChkRemember.setChecked(mSharedPreferences.getBoolean("checked",false));



        mbtnSignIn.setOnClickListener(v -> {
            String email = mEdtEmail.getText().toString().trim();
            String password = mEdtPasswork.getText().toString().trim();
            if (email.equals("duclam08@") && password.equals("1234")){
                Toast.makeText(SharePreferenceActivity.this,"Sign In Seccessfull",Toast.LENGTH_SHORT).show();
                if (mChkRemember.isChecked()){
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("account", email);
                    editor.putString("password", password);
                    editor.putBoolean("checked", true);
                    editor.apply();

                }else {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.remove("account");
                    editor.remove("password");
                    editor.remove("checked");
                    editor.apply();
                }

            }else {
                Toast.makeText(SharePreferenceActivity.this,"Sign In Fail !",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mEdtEmail = findViewById(R.id.edtEmail);
        mEdtPasswork = findViewById(R.id.edtPassword);
        mChkRemember = findViewById(R.id.chkRemember);
        mbtnSignIn = findViewById(R.id.btnLogin);
    }
}
