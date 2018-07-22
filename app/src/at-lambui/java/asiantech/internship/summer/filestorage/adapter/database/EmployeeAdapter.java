package asiantech.internship.summer.filestorage.adapter.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.DatabaseActivity;
import asiantech.internship.summer.filestorage.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter {
    private List<Employee> mEmployeeList;
    private Context mContext;
    private DatabaseActivity.DeleteEmployeeListener mDeleteLister;

    public EmployeeAdapter(List<Employee> mEmployeeList, Context context, DatabaseActivity.DeleteEmployeeListener listener) {
        this.mContext = context;
        this.mEmployeeList = mEmployeeList;
        this.mDeleteLister = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_employee, viewGroup, false);
        return new EmployeeViewHolder(itemView, mEmployeeList, mContext, mDeleteLister);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(((EmployeeViewHolder) holder), mEmployeeList.get(position));
    }

    private void onBindViewHolder(EmployeeViewHolder employeeViewHolder, Employee employee) {
        employeeViewHolder.employeeId = employee.getId();
        employeeViewHolder.getTvEmployeeName().setText(employee.getName());
        employeeViewHolder.getTvEmployeeAddress().setText(employee.getAddress());
        employeeViewHolder.getTvEmployeePhone().setText(employee.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }
}
