package asiantech.internship.summer.file_storage.database.view.company_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class CompanyViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvCompanyCode;
    private final TextView mTvCompanyName;
    private int mCompanyId;
    private CompanyFragment.OnFragmentInteractionListener mListener;

    public TextView getTvCompanyCode() {
        return mTvCompanyCode;
    }

    public TextView getTvCompanyName() {
        return mTvCompanyName;
    }

    public void setCompanyId(int companyId) {
        this.mCompanyId = companyId;
    }

    CompanyViewHolder(View itemView, CompanyFragment.OnFragmentInteractionListener listener) {
        super(itemView);
        mListener = listener;
        mTvCompanyCode = itemView.findViewById(R.id.tvCompanyCode);
        mTvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        itemView.setOnClickListener(view -> mListener.openEmployeeFragment(mCompanyId));
    }
}
