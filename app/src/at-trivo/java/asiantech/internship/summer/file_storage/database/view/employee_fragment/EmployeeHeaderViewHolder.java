package asiantech.internship.summer.file_storage.database.view.employee_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class EmployeeHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvTitle;

    public TextView getTvTitle() {
        return mTvTitle;
    }

    EmployeeHeaderViewHolder(View itemView) {
        super(itemView);
        mTvTitle = itemView.findViewById(R.id.tvTitle);
    }
}
