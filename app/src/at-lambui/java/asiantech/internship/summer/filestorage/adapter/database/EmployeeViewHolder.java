package asiantech.internship.summer.filestorage.adapter.database;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.DatabaseActivity;
import asiantech.internship.summer.filestorage.model.Employee;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvEmployeeName;
    private TextView mTvEmployeeAddress;
    private TextView mTvEmployeePhone;
    public int employeeId;
    private List<Employee> mEmployeeList;
    private Context mContext;
    private DatabaseActivity.DeleteEmployeeListener mListener;

    EmployeeViewHolder(View itemView, List<Employee> employeeList, Context context, DatabaseActivity.DeleteEmployeeListener listener) {
        super(itemView);
        this.mContext = context;
        this.mListener = listener;
        this.mEmployeeList = employeeList;

        mTvEmployeeName = itemView.findViewById(R.id.tvEployeeName);
        mTvEmployeeAddress = itemView.findViewById(R.id.tvEmployeeAddress);
        mTvEmployeePhone = itemView.findViewById(R.id.tvEmployeePhoneNumber);
        itemView.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.delete_employee)
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel())
                    .setPositiveButton(R.string.yes, (dialog, id) -> mListener.deleteEmployeeOnClick(mEmployeeList.get(getAdapterPosition())));
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public TextView getTvEmployeeName() {
        return mTvEmployeeName;
    }

    public TextView getTvEmployeeAddress() {
        return mTvEmployeeAddress;
    }

    public TextView getTvEmployeePhone() {
        return mTvEmployeePhone;
    }
}