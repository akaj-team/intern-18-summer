package asiantech.internship.summer.file_storage.sqlite.view.company_fragment;

import android.content.Context;
import android.os.Bundle;

import java.util.Objects;

import asiantech.internship.summer.file_storage.sqlite.database.CompanyDao;
import asiantech.internship.summer.file_storage.sqlite.view.SharedFragment;
import asiantech.internship.summer.file_storage.sqlite.view.popup.AddCompanyDialogFragment;

public class CompanyFragment extends SharedFragment {

    private OnFragmentInteractionListener mListener;
    private CompanyDao mCompanyDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompanyDao = new CompanyDao(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    protected void setUpRecyclerView() {
        super.setUpRecyclerView();
        CompanyAdapter adapter = new CompanyAdapter(mDataSet, mListener);
        mRecyclerView.setAdapter(adapter);
        updateCompanies();
    }

    protected void onAddButtonClicked() {
        AddCompanyDialogFragment addCompanyDialog = new AddCompanyDialogFragment();
        addCompanyDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), AddCompanyDialogFragment.ADD_COMPANY_DIALOG_TAG);
    }

    public void updateCompanies() {
        mDataSet.clear();
        mDataSet.addAll(mCompanyDao.getCompanies());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void openEmployeeFragment(int companyId);
    }
}
