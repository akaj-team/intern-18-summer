package asiantech.internship.summer.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSharePreference;
    private Button btnInternalExternal;
    private Button btnDatabaseSQLite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        
        initView();
        addListener();
    }

    private void initView(){
        btnSharePreference = findViewById(R.id.btnSharePreference);
        btnInternalExternal = findViewById(R.id.btnInternalExternal);
        btnDatabaseSQLite = findViewById(R.id.btnDatabase);
    }

    private void addListener() {
        btnSharePreference.setOnClickListener(this);
        btnInternalExternal.setOnClickListener(this);
        btnDatabaseSQLite.setOnClickListener(this);
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
                break;
            case R.id.btnDatabase:
                break;
        }
    }
}
