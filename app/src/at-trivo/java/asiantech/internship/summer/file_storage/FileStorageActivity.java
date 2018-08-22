package asiantech.internship.summer.file_storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.internal_and_external.InternalAndExternalActivity;
import asiantech.internship.summer.file_storage.share_preference.SharePreferenceActivity;
import asiantech.internship.summer.file_storage.sqlite.view.DatabaseActivity;

public class FileStorageActivity extends AppCompatActivity {

    private SparseArray<Class> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);

        mMap = new SparseArray<>();
        mMap.put(R.id.bttSharePreference, SharePreferenceActivity.class);
        mMap.put(R.id.bttInternalAndExternal, InternalAndExternalActivity.class);
        mMap.put(R.id.bttDatabase, DatabaseActivity.class);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, mMap.get(view.getId()));
        startActivity(intent);
    }
}
