package asiantech.internship.summer.storage.fragment.database;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.adpater.database.EmployeeAdapter;
import asiantech.internship.summer.storage.adpater.database.OnImageItemClickListener;
import asiantech.internship.summer.storage.model.database.database.EmployeeDAO;
import asiantech.internship.summer.storage.model.database.models.Employee;

public class EmployeeFragment extends Fragment implements OnImageItemClickListener {
    private ImageView mImgAddEmployee;
    private ArrayList<Employee> mEmployeeList;
    private EmployeeAdapter mEmployeeAdapter;
    private EmployeeDAO mEmployeeDAO;
    private int mCompanyIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        RecyclerView recyclerViewEmployee = view.findViewById(R.id.recyclerViewEmployee);
        mImgAddEmployee = view.findViewById(R.id.imgAddEmployee);

        mEmployeeDAO = EmployeeDAO.getInstance(getActivity());

        mEmployeeList = new ArrayList<>();
        mEmployeeAdapter = new EmployeeAdapter(getActivity(), mEmployeeList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewEmployee.setLayoutManager(mLayoutManager);
        recyclerViewEmployee.setAdapter(mEmployeeAdapter);

        if (getArguments() != null) {
            mCompanyIndex = getArguments().getInt(CompanyFragment.COMPANY_KEY);
            Toast.makeText(getActivity(), "" + mCompanyIndex, Toast.LENGTH_SHORT).show();
        }

        addListener();
        return view;
    }

    private void addListener() {
        mImgAddEmployee.setOnClickListener(view -> {
            Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
            @SuppressLint("InflateParams") View layout = getActivity().getLayoutInflater().inflate(R.layout.cutom_dialog_add_data, null);
            dialog.setContentView(layout);

            EditText edtEmployeeName = layout.findViewById(R.id.edtAddName);
            Button btnAddEmployee = layout.findViewById(R.id.btnAddName);
            btnAddEmployee.setOnClickListener(view1 -> {
                String employName = edtEmployeeName.getText().toString();
                if (employName.length() > 6) {
                    mEmployeeDAO.insertEmployee(mCompanyIndex, employName);
                    mEmployeeList.clear();
                    mEmployeeList.addAll(mEmployeeDAO.getAllEmployee(mCompanyIndex));
                    mEmployeeAdapter.notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(getActivity(), "please enter employee name on 6 characters", Toast.LENGTH_SHORT).show();
                    edtEmployeeName.setText("");
                }
            });
            dialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mEmployeeList.clear();
        mEmployeeList.addAll(mEmployeeDAO.getAllEmployee(mCompanyIndex));
        mEmployeeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageAddClick(int index) {
    }

    @Override
    public void onImageDeleteClick(Employee employee) {
        mEmployeeDAO.deleteEmployee(employee.getIdEmployee());
        mEmployeeList.remove(employee);
        mEmployeeAdapter.notifyDataSetChanged();
    }
}
