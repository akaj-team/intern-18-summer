package asiantech.internship.summer.filestorage.adapter.database;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.DatabaseActivity;

public class CompanyViewHolder extends RecyclerView.ViewHolder {
    public int companyId;
    private TextView mTvCompanyName;
    private TextView mTvCompanyAddress;
    private DatabaseActivity.AddOjectListener mListener;

    CompanyViewHolder(View itemView, DatabaseActivity.AddOjectListener listener) {
        super(itemView);
        mTvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        mTvCompanyAddress = itemView.findViewById(R.id.tvEmployeeAddress);
        this.mListener = listener;
        itemView.setOnClickListener(v -> mListener.addObjectOnClick(companyId));
    }

    public TextView getTvCompanyName() {
        return mTvCompanyName;
    }

    public TextView getTvCompanyAddress() {
        return mTvCompanyAddress;
    }
}
