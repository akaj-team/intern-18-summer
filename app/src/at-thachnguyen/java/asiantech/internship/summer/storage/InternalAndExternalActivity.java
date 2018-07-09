package asiantech.internship.summer.storage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import asiantech.internship.summer.R;

public class InternalAndExternalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnInternal;
    private Button mBtnExternal;
    private EditText mEdtContent;
    private TextView mTvContent;
    private static final String FILE_NAME = "myfile.txt";
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final int REQUEST_READ_STORAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        init();
        mBtnInternal.setOnClickListener(this);
        mBtnExternal.setOnClickListener(this);
    }

    private void init() {
        mBtnInternal = findViewById(R.id.btnInternal);
        mBtnExternal = findViewById(R.id.btnExternal);
        mEdtContent = findViewById(R.id.edtContent);
        mTvContent = findViewById(R.id.tvContent);
    }

    private void internalStore() {
        String content = mEdtContent.getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void internalRead() {
        try {
            FileInputStream inputStream = openFileInput(FILE_NAME);
            int c;
            String temp = "";
            while ((c = inputStream.read()) != -1) {
                temp += (Character.toString((char) c));
            }
            mTvContent.setText("File content is: " + temp);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void externalStore() {
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        } else {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILE_NAME;
            File file = new File(path);
            try {
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                streamWriter.append(mEdtContent.getText().toString());
                streamWriter.close();
                outputStream.close();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void externalRead() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILE_NAME;
        File file = new File(path);
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        } else {
            try {
                String temp;
                String content = "";
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((temp = reader.readLine()) != null) {
                    content += temp + "\n";
                }
                reader.close();
                inputStream.close();
                mTvContent.setText("File content is: " + content);
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInternal:
                internalStore();
                internalRead();
                break;
            case R.id.btnExternal:
                externalStore();
                externalRead();
                break;
        }
    }
}
