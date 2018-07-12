package asiantech.internship.summer.storage.fragment.database;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.adpater.database.DatabaseAdapter;
import asiantech.internship.summer.storage.model.database.database.CompanyDAO;
import asiantech.internship.summer.storage.model.database.models.Company;

public class CompanyFragment extends Fragment {
    private RecyclerView mRecyclerViewCompany;
    private ImageView mImgAddCompany;
    private List<Company> mCompanyList = new ArrayList<>();
    private DatabaseAdapter mDatabaseAdapter;
    private CompanyDAO mCompanyDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        mRecyclerViewCompany = view.findViewById(R.id.recyclerViewCompany);
        mImgAddCompany = view.findViewById(R.id.imgAddCompany);

        mCompanyDAO = CompanyDAO.getInstance(getActivity());
        Log.d("xxxx", "onCreateView: ");

        mDatabaseAdapter = new DatabaseAdapter(getActivity(),mCompanyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewCompany.setLayoutManager(mLayoutManager);
        mRecyclerViewCompany.setAdapter(mDatabaseAdapter);

        addListener();

        return view;
    }

    private void addListener() {

        mImgAddCompany.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getActivity());
            View layout = LayoutInflater.from(getContext()).inflate(R.layout.cutom_dialog_add_data, null);
            dialog.setContentView(layout);
            EditText edtName = layout.findViewById(R.id.edtAddName);
            Button btnAdd = layout.findViewById(R.id.btnAddName);

            btnAdd.setOnClickListener(view1 -> {
                String name = edtName.getText().toString();
                Company company = new Company(name);
                mCompanyDAO.insertCompany(company);
                mCompanyList = mCompanyDAO.getAllCompany();
                mDatabaseAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        mCompanyList = mCompanyDAO.getAllCompany();
//        mDatabaseAdapter.notifyDataSetChanged();
    }
}
