package asiantech.internship.summer.filestorage.fragment.database;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class AddCompanyFragment extends Fragment {
    private EditText mEdtCompanyName;
    private EditText mEdtCompanyAddress;
    private Button mBtnAddNewCompany;
    private Button mBtnCancel;
    public static final String DATA_NAME = "name_receive";
    public static final String DATA_ADDRESS = "address_receive";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_employee,container,false);
        initViews(view);
        mBtnAddNewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String nameCompany= mEdtCompanyName.getText().toString();
               String addressCompany = mEdtCompanyAddress.getText().toString();
               if (nameCompany.equals("") || addressCompany.equals("")){
                   Toast.makeText(getActivity(),"Please input all data",Toast.LENGTH_SHORT).show();
               }else {
                   CompanyFragment companyFragment = new CompanyFragment();
                   Bundle bundle = new Bundle();
                   bundle.putString(DATA_NAME,nameCompany);
                   bundle.putString(DATA_ADDRESS,addressCompany);
                   companyFragment.setArguments(bundle);
                   FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.flContainerFileStorage,companyFragment);


               }

            }
        });
        return view;
    }

    private void initViews(View view) {
        mEdtCompanyName = view.findViewById(R.id.edt_Name_Company);
        mEdtCompanyAddress = view.findViewById(R.id.edt_Address_Company);
        mBtnAddNewCompany = view.findViewById(R.id.btn_Add_employee);
        mBtnCancel = view.findViewById(R.id.btn_Cancel);


    }

}
