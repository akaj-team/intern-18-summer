package asiantech.internship.summer.filestorage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.fragment.database.CompanyFragment;


public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_database);

        CompanyFragment companyFragment = new CompanyFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flContainerFileStorage,companyFragment);
        fragmentTransaction.commit();

    }
}
