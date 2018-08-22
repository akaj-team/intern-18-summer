package asiantech.internship.summer.file_storage.sqlite.view.employee_fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.sqlite.database.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter {

    private List<Object> mDataSet;
    private Activity mActivity;
    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_EMPLOYEE = 1;

    EmployeeAdapter(List<Object> dataSet, Activity activity) {
        mDataSet = dataSet;
        mActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_employee_header, parent, false);
            return new EmployeeHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_employee, parent, false);
            return new EmployeeViewHolder(view, mActivity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == VIEW_TYPE_EMPLOYEE) {
            onBindViewHolder((EmployeeViewHolder) holder, (Employee) mDataSet.get(position));
        } else {
            onBindViewHolder((EmployeeHeaderViewHolder) holder, (String) mDataSet.get(position));
        }
    }

    private void onBindViewHolder(@NonNull EmployeeViewHolder holder, Employee employee) {
        holder.getTvEmployeeName().setText(employee.getName());
        holder.getTvEmployeeGender().setText(employee.isMan() ? R.string.man : R.string.woman);
        holder.setEmployeeId(employee.getId());
    }

    private void onBindViewHolder(@NonNull EmployeeHeaderViewHolder holder, String title) {
        title = title.concat(mActivity.getString(R.string.s_employees));
        holder.getTvTitle().setText(title);
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_EMPLOYEE;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
