package asiantech.internship.summer.filestorage.fragment.database;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import asiantech.internship.summer.filestorage.DatabaseActivity;
import asiantech.internship.summer.filestorage.dao.CompanyDAO;

public class AddCompanyFragment extends Fragment implements DatabaseActivity.AddOjectListener {
    private EditText mEdtCompanyName;
    private EditText mEdtCompanyAddress;
    private Button mBtnAddNewCompany;
    private Button mBtnCancel;
    private CompanyDAO mCompanyDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_company,container,false);
        initViews(view);
        mCompanyDAO = new CompanyDAO(getContext());
        mBtnAddNewCompany.setOnClickListener(v -> {

            String nameCompany= mEdtCompanyName.getText().toString();
            String addressCompany = mEdtCompanyAddress.getText().toString();
           if (nameCompany.equals("") || addressCompany.equals("")){
               Toast.makeText(getActivity(),"Please input all data",Toast.LENGTH_SHORT).show();
           }else {
               mCompanyDAO.insertCompany(nameCompany,addressCompany);
               CompanyFragment companyFragment = new CompanyFragment();
               FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.flContainerFileStorage,companyFragment);
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
           }
        });
        mBtnCancel.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        return view;
    }

    private void initViews(View view) {
        mEdtCompanyName = view.findViewById(R.id.edtNameCompany);
        mEdtCompanyAddress = view.findViewById(R.id.edtAddressCompany);
        mBtnAddNewCompany = view.findViewById(R.id.btnAddCompany);
        mBtnCancel = view.findViewById(R.id.btnCancelCompany);
    }
    @Override
    public void addObjectOnClick(int position) {
        mCompanyDAO = new CompanyDAO(getContext());
    }
}
