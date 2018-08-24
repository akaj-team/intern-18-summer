package asiantech.internship.summer.filestorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class InternalAndExternalAcitivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEdtInput;
    private Button mBtnInternal;
    private Button mBtnExternal;
    private TextView mTvDisplay;
    private static final String TAG = "MyActivity";
    private static final String FILE_NAME = "duclam.txt";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_external);
        initView();
        checkAndRequestPermission();
        mBtnInternal.setOnClickListener(this);
        mBtnExternal.setOnClickListener(this);
    }

    private void initView() {
        mEdtInput = findViewById(R.id.edtInput);
        mBtnInternal = findViewById(R.id.btnInternal);
        mBtnExternal = findViewById(R.id.btnExternal);
        mTvDisplay = findViewById(R.id.tvDisplay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInternal:
                saveDataInternal();
                loadDataInternal();
                break;
            case R.id.btnExternal:
                saveDataExternal();
                loadDataExternal();
                break;
            default:
                break;
        }
    }

    private void saveDataInternal() {
        String getInput = mEdtInput.getText().toString();
        try {
            File file = new File(this.getFilesDir(), FILE_NAME);
            OutputStreamWriter fileOutPut = new OutputStreamWriter( new FileOutputStream(file),StandardCharsets.UTF_8);
            fileOutPut.append(getInput);
            fileOutPut.close();
        } catch (IOException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    private void loadDataInternal() {
        try {
            File file = new File(this.getFilesDir(), FILE_NAME);
            FileInputStream fileReader = new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(fileReader,StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text).append("\n");
            }
            mTvDisplay.setText(stringBuilder.toString());
            fileReader.close();
        } catch (IOException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    private void saveDataExternal() {
        if (isExternalStorageReadable()) {
            String content = mEdtInput.getText().toString();
            try {
                File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
                OutputStreamWriter fileOutPut = new OutputStreamWriter( new FileOutputStream(file),StandardCharsets.UTF_8);
                fileOutPut.append(content);
                fileOutPut.close();
            } catch (IOException e) {
                Log.e(TAG,e.getMessage());
            }
        } else {
            Toast.makeText(this, "not SDCARD or not isExternalStorageReadable", Toast.LENGTH_LONG).show();
        }
    }
    private void loadDataExternal() {
        try {
            File file = new File(this.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
            FileInputStream fileReader = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileReader,StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            mTvDisplay.setText(stringBuilder.toString());
            fileReader.close();

        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
    }

    //permission runtime not Crash.
    private void checkAndRequestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    //check device have external storage,
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}