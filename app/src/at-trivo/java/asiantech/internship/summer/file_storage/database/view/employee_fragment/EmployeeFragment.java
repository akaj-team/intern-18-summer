package asiantech.internship.summer.file_storage.database.view.employee_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.database.data.DatabaseHelper;
import asiantech.internship.summer.file_storage.database.data.model.Employee;
import asiantech.internship.summer.file_storage.database.view.DatabaseActivity;
import asiantech.internship.summer.file_storage.database.view.popup.AddEmployeeDialogFragment;

public class EmployeeFragment extends Fragment {

    private int mCompanyId;
    private String mTitle;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCompanyId = bundle.getInt(DatabaseActivity.COMPANY_ID_KEY);
            String companyName = DatabaseHelper.getInstance(getContext()).getCompanyName(mCompanyId);
            mTitle = companyName.concat(Objects.requireNonNull(getActivity()).getResources().getString(R.string.s_employee));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        setUpRecyclerView();
        setUpAddButton(view);
        updateEmployees();
        return view;
    }

    public void updateEmployees() {
        List<Employee> employees = DatabaseHelper.getInstance(getContext()).getEmployees(mCompanyId);
        EmployeeAdapter mAdapter = new EmployeeAdapter(employees, mTitle, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setUpAddButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            AddEmployeeDialogFragment addEmployeeDialogFragment = new AddEmployeeDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(DatabaseActivity.COMPANY_ID_KEY, mCompanyId);
            addEmployeeDialogFragment.setArguments(bundle);
            addEmployeeDialogFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), AddEmployeeDialogFragment.ADD_EMPLOYEE_DIALOG_TAG);
        });
    }
}
