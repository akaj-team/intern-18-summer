package asiantech.internship.summer.file_storage.database.view.employee_fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.database.data.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter {

    private List<Employee> mDataSet;
    private Activity mActivity;
    private String mTitle;
    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_EMPLOYEE = 1;

    EmployeeAdapter(List<Employee> dataSet, String title, Activity activity) {
        mDataSet = dataSet;
        mTitle = title;
        mActivity=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_employee_header, parent, false);
            return new EmployeeHeaderViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_employee, parent, false);
            return new EmployeeViewHolder(view,mActivity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == VIEW_TYPE_EMPLOYEE) {
            onBindViewHolder((EmployeeViewHolder) holder, mDataSet.get(position-1));
        } else {
            ((EmployeeHeaderViewHolder) holder).getTvTitle().setText(mTitle);
        }
    }

    private void onBindViewHolder(@NonNull EmployeeViewHolder holder, Employee employee) {
        holder.getTvEmployeeName().setText(employee.getName());
        holder.getTvEmployeeNickname().setText(employee.getNickname());
        holder.setEmployeeId(employee.getId());
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_EMPLOYEE;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size() + 1;
    }
}
