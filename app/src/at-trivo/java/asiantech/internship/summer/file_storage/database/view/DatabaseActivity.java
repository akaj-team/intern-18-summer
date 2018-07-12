package asiantech.internship.summer.file_storage.database.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.database.data.DatabaseHelper;
import asiantech.internship.summer.file_storage.database.view.company_fragment.CompanyFragment;
import asiantech.internship.summer.file_storage.database.view.employee_fragment.EmployeeFragment;
import asiantech.internship.summer.file_storage.database.view.popup.AddCompanyDialogFragment;
import asiantech.internship.summer.file_storage.database.view.popup.AddEmployeeDialogFragment;
import asiantech.internship.summer.file_storage.database.view.popup.DeleteEmployeeDialogFragment;

public class DatabaseActivity extends AppCompatActivity
        implements CompanyFragment.OnFragmentInteractionListener, AddCompanyDialogFragment.AddCompanyDialogListener,
        AddEmployeeDialogFragment.AddEmployeeDialogListener, DeleteEmployeeDialogFragment.DeleteEmployeeDialogListener {

    public static final String COMPANY_ID_KEY = "company id key";
    public static final String EMPLOYEE_ID_KEY = "employee id key";
    private CompanyFragment mCompanyFragment;
    private EmployeeFragment mEmployeeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        mCompanyFragment = new CompanyFragment();
        mEmployeeFragment = new EmployeeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flRoot, mCompanyFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openEmployeeFragment(int companyId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(COMPANY_ID_KEY, companyId);
        mEmployeeFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.flRoot, mEmployeeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void addCompany(String code, String name) {
        DatabaseHelper.getInstance(this).insertCompany(code, name);
        if (mCompanyFragment.isVisible()) {
            mCompanyFragment.updateCompanies();
        }
    }

    @Override
    public void addEmployee(String name, String nickname, int companyId) {
        DatabaseHelper.getInstance(this).insertEmployee(name, nickname, companyId);
        if (mEmployeeFragment.isVisible()) {
            mEmployeeFragment.updateEmployees();
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        DatabaseHelper.getInstance(this).deleteEmployee(employeeId);
        if (mEmployeeFragment.isVisible()) {
            mEmployeeFragment.updateEmployees();
        }
    }

    @Override
    public void onBackPressed(){
        if (mEmployeeFragment.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flRoot, mCompanyFragment);
            fragmentTransaction.commit();
        }else{
            super.onBackPressed();
        }
    }
}
