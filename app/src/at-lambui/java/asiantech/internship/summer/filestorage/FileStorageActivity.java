package asiantech.internship.summer.filestorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity {
    private Button mBtnSharePreference;
    private Button mBtnInternalAndExternalStore;
    private Button mBtnDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        initView();
        mBtnSharePreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent  intent = new Intent(FileStorageActivity.this,SharePreferenceActivity.class);
               startActivity(intent);
            }
        });
        mBtnInternalAndExternalStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intentaaa = new Intent(FileStorageActivity.this,InternalAndExternalAcitivity.class);
                startActivity(intentaaa);
            }
        });
        mBtnDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSqlite = new Intent(FileStorageActivity.this,DatabaseActivity.class);

                startActivity(intentSqlite);
            }
        });
//        mBtnSharePreference.setOnClickListener((View.OnClickListener) this);
//        mBtnInternalAndExternalStore.setOnClickListener((View.OnClickListener) this);
//        mBtnDatabase.setOnClickListener((View.OnClickListener) this);
    }


    public void initView() {
         mBtnSharePreference = (Button) findViewById(R.id.btnSharePreference);
         mBtnInternalAndExternalStore = (Button) findViewById(R.id.btnInternalAndExternalStore);
         mBtnDatabase = (Button) findViewById(R.id.btnDatabese);
    }
//    public void onClick(View view){
//        Intent intent;
//        switch (view.getId()){
//            case R.id.btnSharePreference:
//                intent = new Intent(FileStorageActivity.this,SharePreferenceActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btnInternalAndExternalStore:
//                intent = new Intent(FileStorageActivity.this,InternalAndExternalAcitivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btnDatabese:
//                intent = new Intent(FileStorageActivity.this,Database.class);
//                startActivity(intent);
//                break;
//
//        }
//
//    }




}
