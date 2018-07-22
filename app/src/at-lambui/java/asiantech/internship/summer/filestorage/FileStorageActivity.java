package asiantech.internship.summer.filestorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSharePreference;
    private Button mBtnInternalAndExternalStore;
    private Button mBtnDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        initView();
        mBtnSharePreference.setOnClickListener(this);
        mBtnInternalAndExternalStore.setOnClickListener(this);
        mBtnDatabase.setOnClickListener(this);
    }

    public void initView() {
        mBtnSharePreference = findViewById(R.id.btnSharePreference);
        mBtnInternalAndExternalStore = findViewById(R.id.btnInternalAndExternalStore);
        mBtnDatabase = findViewById(R.id.btnDatabese);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnSharePreference:
                intent = new Intent(FileStorageActivity.this, SharePreferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.btnInternalAndExternalStore:
                intent = new Intent(FileStorageActivity.this, InternalAndExternalAcitivity.class);
                startActivity(intent);
                break;
            case R.id.btnDatabese:
                intent = new Intent(FileStorageActivity.this, DatabaseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
