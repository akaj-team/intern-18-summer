package asiantech.internship.summer.file_storage.database.view.company_fragment;

import android.content.Context;
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
import asiantech.internship.summer.file_storage.database.data.model.Company;
import asiantech.internship.summer.file_storage.database.view.popup.AddCompanyDialogFragment;

public class CompanyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        setUpRecyclerView();
        setUpAddButton(view);
        updateCompanies();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateCompanies() {
        List<Company> companies = DatabaseHelper.getInstance(getContext()).getCompanies();
        CompanyAdapter mAdapter = new CompanyAdapter(companies, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setUpAddButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            AddCompanyDialogFragment addCompanyDialog = new AddCompanyDialogFragment();
            addCompanyDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), AddCompanyDialogFragment.ADD_COMPANY_DIALOG_TAG);
        });
    }

    public interface OnFragmentInteractionListener {
        void openEmployeeFragment(int companyId);
    }
}
