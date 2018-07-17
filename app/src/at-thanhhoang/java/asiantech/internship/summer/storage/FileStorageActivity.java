package asiantech.internship.summer.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TITLE_TOOLBAR = "File Storage";
    private Button mBtnSharePreference;
    private Button mBtnInternalExternal;
    private Button mBtnDatabaseSQLite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        initView();
        addListener();
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbarFileStorage);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);

        mBtnSharePreference = findViewById(R.id.btnSharePreference);
        mBtnInternalExternal = findViewById(R.id.btnInternalExternal);
        mBtnDatabaseSQLite = findViewById(R.id.btnDatabase);
    }

    private void addListener() {
        mBtnSharePreference.setOnClickListener(this);
        mBtnInternalExternal.setOnClickListener(this);
        mBtnDatabaseSQLite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnSharePreference:
                intent = new Intent(this, SharePreferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.btnInternalExternal:
                intent = new Intent(this, InternalExternalActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDatabase:
                intent = new Intent(this, DatabaseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
