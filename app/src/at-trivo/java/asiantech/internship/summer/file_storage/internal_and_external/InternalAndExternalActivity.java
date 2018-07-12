package asiantech.internship.summer.file_storage.internal_and_external;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import asiantech.internship.summer.R;

public class InternalAndExternalActivity extends AppCompatActivity {
    
    private final String INTERNAL_FILE_NAME = "internal.txt";
    private final String EXTERNAL_FILE_NAME = "external.txt";
    private TextView mTvSaved;
    private EditText mEdtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_and_external);
        setUpViewComponents();
        setUpInternalSave();
        setUpExternalSave();
    }

    private void setUpViewComponents() {
        mTvSaved = findViewById(R.id.tvSaved);
        mEdtContent = findViewById(R.id.edtContent);
    }

    private void setUpInternalSave() {
        Button bttInternal = findViewById(R.id.bttInternal);
        bttInternal.setOnClickListener(view1 -> {
            File file = new File(Objects.requireNonNull(this).getFilesDir(), INTERNAL_FILE_NAME);
            handleFile(file);
        });
    }

    private void setUpExternalSave() {
        requestPermission();
        Button bttExternal = findViewById(R.id.bttExternal);
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            bttExternal.setVisibility(View.INVISIBLE);
        } else {
            bttExternal.setOnClickListener(view1 -> {
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), EXTERNAL_FILE_NAME);
                handleFile(file);
            });
        }
    }

    private void handleFile(File file) {
        try {
            String savingText = mEdtContent.getText().toString();
            saveStringToFile(file, savingText);
            String savedText = getStringFromFile(file);
            mTvSaved.setText(savedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStringToFile(File file, String savingText) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(savingText);
        fileWriter.close();
    }

    private String getStringFromFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String savedText = bufferedReader.readLine();
        bufferedReader.close();
        bufferedReader.close();
        return savedText;
    }

    private void requestPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
