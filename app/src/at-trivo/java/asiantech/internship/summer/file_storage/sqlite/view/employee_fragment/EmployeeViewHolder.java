package asiantech.internship.summer.file_storage.sqlite.view.employee_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.sqlite.view.DatabaseActivity;
import asiantech.internship.summer.file_storage.sqlite.view.popup.DeleteEmployeeDialogFragment;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvEmployeeName;
    private final TextView mTvEmployeeGender;
    private int mEmployeeId;
    private Activity mActivity;

    EmployeeViewHolder(View itemView, Activity activity) {
        super(itemView);
        mActivity = activity;
        mTvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
        mTvEmployeeGender = itemView.findViewById(R.id.tvEmployeeGender);
        itemView.setOnClickListener(view -> {
            DeleteEmployeeDialogFragment deleteEmployeeDialogFragment = new DeleteEmployeeDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(DatabaseActivity.EMPLOYEE_ID_KEY, mEmployeeId);
            deleteEmployeeDialogFragment.setArguments(bundle);
            deleteEmployeeDialogFragment.show(mActivity.getFragmentManager(), DeleteEmployeeDialogFragment.DELETE_EMPLOYEE_DIALOG_TAG);
        });
    }

    public TextView getTvEmployeeName() {
        return mTvEmployeeName;
    }

    public TextView getTvEmployeeGender() {
        return mTvEmployeeGender;
    }

    public void setEmployeeId(int employeeId) {
        mEmployeeId = employeeId;
    }
}
