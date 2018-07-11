package asiantech.internship.summer.storage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.model.Employee;

@SuppressLint("Registered")
public class ListEmployeeActivity extends AppCompatActivity {
    private ManageDatabaseHelper mManageDatabase;
    ManageAdapter mManageAdapter;
    List<Employee> mEmployees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);
        RecyclerView recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employees");
        Bundle bundle = this.getIntent().getExtras();
        int idFirm = bundle.getInt(ManageAdapter.ID_FIRM);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewEmployees.setLayoutManager(manager);
        mManageDatabase = new ManageDatabaseHelper(this, Employee.TABLE_NAME_EMPLOYEE, Employee.CREATE_TABLE_EMPLOYEE);
        mManageAdapter = new ManageAdapter(mEmployees, true, this, (int position) -> dialogDelete(mEmployees.get(position), idFirm));
        recyclerViewEmployees.setAdapter(mManageAdapter);
        mEmployees.addAll(mManageDatabase.getEmployees(idFirm));
        mManageAdapter.notifyDataSetChanged();
    }

    private void dialogDelete(Employee employee, int idFirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Do you delete employee " + employee.getmName() + "?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            mManageDatabase.deleteEmployee(employee.getmId());
            mEmployees.clear();
            mEmployees.addAll(mManageDatabase.getEmployees(idFirm));
            mManageAdapter.notifyDataSetChanged();

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
