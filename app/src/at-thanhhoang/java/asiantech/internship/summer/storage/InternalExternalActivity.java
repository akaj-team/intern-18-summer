package asiantech.internship.summer.storage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class InternalExternalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = InternalExternalActivity.class.getSimpleName();
    private static final String FILE_NAME_INTERNAL = "Internal.txt";
    private static final String FILE_NAME_EXTERNAL = "External.txt";
    private static final String TITLE_TOOLBAR = "Internal External";
    private EditText mEdtInputText;
    private Button mBtnInternal;
    private Button mBtnExternal;
    private TextView mTvContent;

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_external);
        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarInternal);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);

        mEdtInputText = findViewById(R.id.edtInputText);
        mBtnInternal = findViewById(R.id.btnInternal);
        mBtnExternal = findViewById(R.id.btnExternal);
        mTvContent = findViewById(R.id.tvContent);
    }

    private void addListener() {
        mBtnInternal.setOnClickListener(this);
        mBtnExternal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInternal:
                saveInternalFile();
                readInternalFile();
                break;
            case R.id.btnExternal:
                if (isStoragePermissionGranted()) {
                    saveExternalFile();
                    readExternalFile();
                }
                break;
        }
    }

    private void saveInternalFile() {
        File internalFile = getFileStreamPath(FILE_NAME_INTERNAL);
        if (internalFile.exists()) {
            Toast.makeText(getApplicationContext(), "exists internal File", Toast.LENGTH_SHORT).show();
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(internalFile, true), StandardCharsets.UTF_8));
                out.write(mEdtInputText.getText().toString());
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.d(TAG, "saveInternalFile: " + e);
            }
            mEdtInputText.setText("");
        } else {
            try {
                Toast.makeText(getApplicationContext(), "not exists internal File", Toast.LENGTH_SHORT).show();
                FileOutputStream outputStream = openFileOutput(FILE_NAME_INTERNAL, Context.MODE_PRIVATE);
                outputStream.write(mEdtInputText.getText().toString().getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "saveInternalFile: " + e);
            }
        }
    }

    private void readInternalFile() {
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME_INTERNAL);
            DataInputStream in = new DataInputStream(fileInputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            mTvContent.setText(builder.toString());
        } catch (Exception e) {
            Log.d(TAG, "readInternalFile: " + e);
        }
    }

    private void saveExternalFile() {
        if (isExternalStorageWritable()) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/MyFile");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File externalFile = new File(dir, FILE_NAME_EXTERNAL);
            if (externalFile.exists()) {
                Toast.makeText(this, "exists external File", Toast.LENGTH_SHORT).show();
                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(externalFile, true), StandardCharsets.UTF_8));
                    out.write(mEdtInputText.getText().toString());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.d(TAG, "saveExternalFile: " + e);
                }
                mEdtInputText.setText("");
            } else {
                try {
                    Toast.makeText(this, "not exists external File", Toast.LENGTH_SHORT).show();
                    FileOutputStream fileOutputStream = new FileOutputStream(externalFile);
                    fileOutputStream.write(mEdtInputText.getText().toString().getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "saveExternalFile: " + e);
                }
            }
        }
    }

    private void readExternalFile() {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/MyFile");
        File externalFile = new File(dir, FILE_NAME_EXTERNAL);
        String line;
        try {
            FileInputStream fileInputStream = new FileInputStream(externalFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            mTvContent.setText(builder.toString());
        } catch (IOException e) {
            Log.d(TAG, "readExternalFile: " + e);
        }
    }
}
