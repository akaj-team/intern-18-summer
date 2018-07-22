package asiantech.internship.summer.filestorage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.fragment.database.CompanyFragment;
import asiantech.internship.summer.filestorage.model.Employee;
public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        CompanyFragment companyFragment = new CompanyFragment();
        replaceFragment(companyFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flContainerFileStorage, fragment);
        fragmentTransaction.commit();
    }
    public interface AddOjectListener {
        void addObjectOnClick(int companyId);
    }

    public interface DeleteEmployeeListener {
        void deleteEmployeeOnClick(Employee employee);
    }
}
