package asiantech.internship.summer.storage.adpater.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.model.database.models.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Employee> mEmployeeList;
    private OnImageItemClickListener mListener;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList, OnImageItemClickListener listener) {
        this.mContext = context;
        this.mEmployeeList = employeeList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_employee, parent, false);
        return new EmployeeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder holder, int position) {
        Employee employee = mEmployeeList.get(position);
        holder.tvEmployeeName.setText(employee.getNameEmployee());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvEmployeeName;
        private final ImageView imgDeleteEmployee;

        MyViewHolder(View itemView) {
            super(itemView);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            imgDeleteEmployee = itemView.findViewById(R.id.imgDeleteEmployee);
            imgDeleteEmployee.setOnClickListener(view -> mListener.onImageDeleteClick(mEmployeeList.get(getAdapterPosition())));
        }
    }
}
