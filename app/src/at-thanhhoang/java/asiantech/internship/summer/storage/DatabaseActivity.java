package asiantech.internship.summer.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.fragment.database.CompanyFragment;

public class DatabaseActivity extends AppCompatActivity implements CompanyFragment.OnReplaceListener {
    private static final String TITLE_TOOLBAR = "Database SQLite";
    private CompanyFragment mCompanyFragment = new CompanyFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initView();
        addListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarDatabase);
        toolbar.setTitle(TITLE_TOOLBAR);
        setSupportActionBar(toolbar);
    }

    private void addListener() {
        replaceFragment(mCompanyFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerDatabase, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onReplaceFragment(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else if (count > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }
}
