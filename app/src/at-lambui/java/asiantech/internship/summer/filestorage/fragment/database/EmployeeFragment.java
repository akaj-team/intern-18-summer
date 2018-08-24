package asiantech.internship.summer.filestorage.fragment.database;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.DatabaseActivity;
import asiantech.internship.summer.filestorage.adapter.database.EmployeeAdapter;
import asiantech.internship.summer.filestorage.dao.EmployeeDAO;
import asiantech.internship.summer.filestorage.model.Employee;

public class EmployeeFragment extends Fragment implements View.OnClickListener, DatabaseActivity.DeleteEmployeeListener {
    private RecyclerView mRecyclerViewListEmployee;
    private EmployeeAdapter mEmployeeAdapter;
    public List<Employee> employeeList;
    private ImageView mImgAdd;
    private EmployeeDAO mEmployeeDAO;
    private static final String TITLE = "List Employee";
    private int mIdKeyCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        initViews(view);
        Bundle bundle = getArguments();
        /*id company */
        if (bundle != null) {
            mIdKeyCompany = (int) bundle.get(CompanyFragment.KEY_ID_COMPANY);
        }
        mRecyclerViewListEmployee.setHasFixedSize(true);
        //select company do len recyceleerview.
        mEmployeeDAO = new EmployeeDAO(getContext());
        employeeList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewListEmployee.setLayoutManager(linearLayoutManager);
        mEmployeeAdapter = new EmployeeAdapter(employeeList, getContext(), this);
        mRecyclerViewListEmployee.setAdapter(mEmployeeAdapter);
        //updata recyclerview
        updateData(mEmployeeDAO.getEmployeesOfCompany(mIdKeyCompany));
        //create khung
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewListEmployee.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.custom_item);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(drawable));
        mRecyclerViewListEmployee.addItemDecoration(dividerItemDecoration);

        mImgAdd.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mImgAdd = view.findViewById(R.id.imgAdd);
        mRecyclerViewListEmployee = view.findViewById(R.id.recyclerViewCompany);
        Toolbar actionBarToolbar = view.findViewById(R.id.toolbar);
        actionBarToolbar.setTitle(TITLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAdd: {
                AddEmployeeFragment addCompanyFragment = new AddEmployeeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CompanyFragment.KEY_ID_COMPANY, mIdKeyCompany);
                addCompanyFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerFileStorage, addCompanyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
        }
    }

    public void updateData(List<Employee> employees) {
        employeeList.clear();
        employeeList.addAll(employees);
        mEmployeeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEmployeeDAO.close();
    }

    @Override
    public void deleteEmployeeOnClick(Employee employee) {
        mEmployeeDAO.deleteEmployee(employee.getId());
        //tim positon cua id.
        // int position = employeeList.get();
        employeeList.remove(employee);
        mEmployeeAdapter.notifyDataSetChanged();
    }
}
