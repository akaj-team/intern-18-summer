package asiantech.internship.summer.filestorage.fragment.database;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.dao.EmployeeDAO;

public class AddEmployeeFragment extends Fragment {
    private EditText mEdtNameEmployee;
    private EditText mEdtAddressEmployee;
    private EditText mEdtPhoneEmployee;
    private EmployeeDAO mEmployeeDAO;
    private EmployeeFragment mEmployeeFragment;
    private int mId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_employee, container, false);
        mEdtNameEmployee = view.findViewById(R.id.edtNameEmployee);
        mEdtAddressEmployee = view.findViewById(R.id.edtAddressEmployee);
        mEdtPhoneEmployee = view.findViewById(R.id.edtPhoneNumberEmployee);
        Button mBtnAddEmployee = view.findViewById(R.id.btnAddEmployee);
        Button mBtnCancelEmployee = view.findViewById(R.id.btnCancelEmployee);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = (int) bundle.get(CompanyFragment.KEY_ID_COMPANY);
        }
        // create companyDAO, employeeDAO;
        mEmployeeDAO = new EmployeeDAO(getContext());
        mBtnAddEmployee.setOnClickListener(v -> {

            String nameEmployee = mEdtNameEmployee.getText().toString();
            String addressEmployee = mEdtAddressEmployee.getText().toString();
            String phonenumber = mEdtPhoneEmployee.getText().toString();
            if (nameEmployee.equals("") || addressEmployee.equals("") || phonenumber.equals("")) {
                Toast.makeText(getActivity(), "Please input all data", Toast.LENGTH_SHORT).show();
            } else {

                mEmployeeDAO.insertEmployee(nameEmployee, addressEmployee, phonenumber, mId);
                mEmployeeFragment = new EmployeeFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt(CompanyFragment.KEY_ID_COMPANY, mId);
                mEmployeeFragment.setArguments(bundle1);
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerFileStorage, mEmployeeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mBtnCancelEmployee.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        return view;
    }
}
