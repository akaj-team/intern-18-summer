package asiantech.internship.summer.storage.fragment.database;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import asiantech.internship.summer.storage.adpater.database.CompanyAdapter;
import asiantech.internship.summer.storage.adpater.database.OnImageItemClickListener;
import asiantech.internship.summer.storage.model.database.database.CompanyDAO;
import asiantech.internship.summer.storage.model.database.models.Company;
import asiantech.internship.summer.storage.model.database.models.Employee;

public class CompanyFragment extends Fragment implements OnImageItemClickListener {
    public static final String COMPANY_KEY = "indexCompany";
    private ImageView mImgAddCompany;

    private CompanyAdapter mCompanyAdapter;
    private ArrayList<Company> mCompanyList;
    private CompanyDAO mCompanyDAO;

    private OnReplaceListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        RecyclerView recyclerViewCompany = view.findViewById(R.id.recyclerViewCompany);
        mImgAddCompany = view.findViewById(R.id.imgAddCompany);

        mCompanyDAO = CompanyDAO.getInstance(getActivity());

        mCompanyList = new ArrayList<>();
        mCompanyAdapter = new CompanyAdapter(getActivity(), mCompanyList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCompany.setLayoutManager(mLayoutManager);
        recyclerViewCompany.setAdapter(mCompanyAdapter);

        addListener();
        return view;
    }

    private void addListener() {
        mImgAddCompany.setOnClickListener(view -> {
            Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
            @SuppressLint("InflateParams") View layout = getActivity().getLayoutInflater().inflate(R.layout.cutom_dialog_add_data, null);
            dialog.setContentView(layout);

            EditText edtCompanyName = layout.findViewById(R.id.edtAddName);
            Button btnAddCompany = layout.findViewById(R.id.btnAddName);
            btnAddCompany.setOnClickListener(view1 -> {
                String companyName = edtCompanyName.getText().toString();
                if (companyName.length() > 3) {
                    mCompanyDAO.insertCompany(companyName);
                    mCompanyList.clear();
                    mCompanyList.addAll(mCompanyDAO.getAllCompany());
                    mCompanyAdapter.notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(getActivity(), "please enter company name on 3 characters", Toast.LENGTH_SHORT).show();
                    edtCompanyName.setText("");
                }
            });
            dialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompanyList.clear();
        mCompanyList.addAll(mCompanyDAO.getAllCompany());
        mCompanyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageAddClick(int index) {
        EmployeeFragment employeeFragment = new EmployeeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COMPANY_KEY, index);
        employeeFragment.setArguments(bundle);
        mListener.onReplaceFragment(employeeFragment);
    }

    @Override
    public void onImageDeleteClick(Employee employee) {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnReplaceListener) getActivity();
    }

    public interface OnReplaceListener {
        void onReplaceFragment(Fragment fragment);
    }
}
