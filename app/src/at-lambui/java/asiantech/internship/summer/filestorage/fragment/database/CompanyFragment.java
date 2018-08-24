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
import asiantech.internship.summer.filestorage.adapter.database.CompanyAdapter;
import asiantech.internship.summer.filestorage.dao.CompanyDAO;
import asiantech.internship.summer.filestorage.model.Company;

public class CompanyFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerViewListCompany;
    private CompanyAdapter mCompanyAdapter;
    public List<Company> companyList;
    private ImageView mImgAdd;
    private CompanyDAO mCompanyDAO;
    public static final String KEY_ID_COMPANY = "KeyIdCompany";
    private static final String TITLE = "List Company";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        companyList = new ArrayList<>();
        DatabaseActivity.AddOjectListener listener = companyId -> {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_ID_COMPANY, companyId);
            EmployeeFragment employeeFragment = new EmployeeFragment();
            employeeFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainerFileStorage, employeeFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
        initViews(view);
        mRecyclerViewListCompany = view.findViewById(R.id.recyclerViewCompany);
        mRecyclerViewListCompany.setHasFixedSize(true);
        //select company do len recyceleerview.
        mCompanyDAO = new CompanyDAO(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewListCompany.setLayoutManager(linearLayoutManager);
        mCompanyAdapter = new CompanyAdapter(companyList, listener);
        mRecyclerViewListCompany.setAdapter(mCompanyAdapter);
        updateData(mCompanyDAO.selectCompany());
        /*create khung*/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewListCompany.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.custom_item);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(drawable));
        mRecyclerViewListCompany.addItemDecoration(dividerItemDecoration);
        /*create khung*/
        mImgAdd.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mImgAdd = view.findViewById(R.id.imgAdd);
        mRecyclerViewListCompany = view.findViewById(R.id.recyclerViewCompany);
        Toolbar actionBarToolbar = view.findViewById(R.id.toolbar);
        actionBarToolbar.setTitle(TITLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAdd: {
                AddCompanyFragment addCompanyFragment = new AddCompanyFragment();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerFileStorage, addCompanyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
        }
    }

    public void updateData(List<Company> companies) {
        companyList.clear();
        companyList.addAll(companies);
        mCompanyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompanyDAO.close();
    }
}
