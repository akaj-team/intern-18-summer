package asiantech.internship.summer.storage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class StorageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSharePreference;
    private Button mBtnStore;
    private Button mBtnSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        init();
        mBtnSharePreference.setOnClickListener(this);
        mBtnStore.setOnClickListener(this);
        mBtnSqlite.setOnClickListener(this);
    }

    private void init() {
        mBtnSharePreference = findViewById(R.id.btnSharePreference);
        mBtnStore = findViewById(R.id.btnStore);
        mBtnSqlite = findViewById(R.id.btnSqlite);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnSharePreference:
                intent = new Intent(StorageActivity.this, SharePreferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.btnStore:
                intent = new Intent(StorageActivity.this, InternalAndExternalActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSqlite:
                intent = new Intent(StorageActivity.this, SqliteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
