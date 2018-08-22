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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveStringToFile(File file, String savingText) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file, true);
        outputStream.write(savingText.getBytes(StandardCharsets.UTF_8));
    }

    private String getStringFromFile(File file) throws IOException {
        int length = (int) file.length();
        byte[] buffer = new byte[length];
        FileInputStream inputStream = new FileInputStream(file);

        int offset = 0;
        while (offset < length) {
            int over = inputStream.read(buffer, offset, length - offset);
            if (over == 0) {
                break;
            }
            offset += over;
        }
        return new String(buffer, StandardCharsets.UTF_8);
    }

    private void requestPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
