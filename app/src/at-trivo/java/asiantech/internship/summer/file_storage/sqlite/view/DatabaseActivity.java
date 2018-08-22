package asiantech.internship.summer.file_storage.sqlite.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.sqlite.database.CompanyDao;
import asiantech.internship.summer.file_storage.sqlite.database.EmployeeDao;
import asiantech.internship.summer.file_storage.sqlite.view.company_fragment.CompanyFragment;
import asiantech.internship.summer.file_storage.sqlite.view.employee_fragment.EmployeeFragment;
import asiantech.internship.summer.file_storage.sqlite.view.popup.AddCompanyDialogFragment;
import asiantech.internship.summer.file_storage.sqlite.view.popup.AddEmployeeDialogFragment;
import asiantech.internship.summer.file_storage.sqlite.view.popup.DeleteEmployeeDialogFragment;

public class DatabaseActivity extends AppCompatActivity
        implements CompanyFragment.OnFragmentInteractionListener, AddCompanyDialogFragment.AddCompanyDialogListener,
        AddEmployeeDialogFragment.AddEmployeeDialogListener, DeleteEmployeeDialogFragment.DeleteEmployeeDialogListener {

    public static final String COMPANY_ID_KEY = "company id key";
    public static final String COMPANY_NAME_KEY = "company name key";
    public static final String EMPLOYEE_ID_KEY = "employee id key";
    private EmployeeDao mEmployeeDao;
    private CompanyDao mCompanyDao;
    private CompanyFragment mCompanyFragment;
    private EmployeeFragment mEmployeeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        mCompanyFragment = new CompanyFragment();
        mEmployeeFragment = new EmployeeFragment();
        mEmployeeDao = new EmployeeDao(this);
        mCompanyDao = new CompanyDao(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flRoot, mCompanyFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openEmployeeFragment(int companyId) {
        String companyName = mCompanyDao.getCompanyName(companyId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(COMPANY_ID_KEY, companyId);
        bundle.putString(COMPANY_NAME_KEY, companyName);
        mEmployeeFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.flRoot, mEmployeeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void addCompany(String name, String address) {
        mCompanyDao.insertCompany(name, address);
        if (mCompanyFragment.isVisible()) {
            mCompanyFragment.updateCompanies();
        }
    }

    @Override
    public void addEmployee(String name, boolean gender, int companyId) {
        mEmployeeDao.insertEmployee(name, gender, companyId);
        if (mEmployeeFragment.isVisible()) {
            mEmployeeFragment.updateEmployees();
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        mEmployeeDao.deleteEmployee(employeeId);
        if (mEmployeeFragment.isVisible()) {
            mEmployeeFragment.updateEmployees();
        }
    }

    @Override
    public void onBackPressed() {
        if (mEmployeeFragment.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flRoot, mCompanyFragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }
}
