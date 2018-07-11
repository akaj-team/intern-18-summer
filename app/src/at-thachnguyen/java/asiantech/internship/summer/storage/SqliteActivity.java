package asiantech.internship.summer.storage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.model.Employee;
import asiantech.internship.summer.storage.model.Firm;

@SuppressLint("Registered")
public class SqliteActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private List<Firm> mFirms;
    private RecyclerView mRecyclerViewFirms;
    private ImageView mImgAdd;
    private ManageDatabaseHelper mManageDatabase;
    private ManageAdapter mManageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        init();
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Firms");
        mFirms = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerViewFirms.setLayoutManager(manager);
        mManageAdapter = new ManageAdapter(false, this, mFirms, (int position) -> dialogAdd("Add Employee", true, mFirms.get(position).getmId()));
        mManageDatabase = new ManageDatabaseHelper(this, Firm.TABLE_NAME_FIRM, Firm.CREATE_TABLE_FIRM);
        mRecyclerViewFirms.setAdapter(mManageAdapter);
        mFirms.addAll(mManageDatabase.getAllFirms());
        mManageAdapter.notifyDataSetChanged();
        mImgAdd.setOnClickListener(v -> dialogAdd("Add Firm", false, 0));
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mImgAdd = findViewById(R.id.imgAdd);
        mRecyclerViewFirms = findViewById(R.id.recyclerViewFirms);
    }

    private void dialogAdd(String title, boolean isAddEmployee, int idFirm) {
        EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String name;
            name = input.getText().toString();
            if (isAddEmployee) {
                mManageDatabase = new ManageDatabaseHelper(this, Employee.TABLE_NAME_EMPLOYEE, Employee.CREATE_TABLE_EMPLOYEE);
                mManageDatabase.insertEmployee(name, idFirm);
            } else {
                mManageDatabase.insertFirm(name);
                mFirms.clear();
                mFirms.addAll(mManageDatabase.getAllFirms());
                mManageAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
