package asiantech.internship.summer.storage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {
    private List<String> mListEmployee;

    public EmployeeAdapter(List<String> mListEmployee) {
        this.mListEmployee = mListEmployee;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_employee, parent, false);
        return new EmployeeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        holder.mTvEmployee.setText(mListEmployee.get(position));
    }

    @Override
    public int getItemCount() {
        return mListEmployee.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {
        TextView mTvEmployee;
        ImageView mImgDelete;
        ImageView mImgInsert;

        EmployeeHolder(View itemView) {
            super(itemView);
            mTvEmployee = itemView.findViewById(R.id.tvEmployee);
            mImgDelete = itemView.findViewById(R.id.imgDelete);
            mImgInsert = itemView.findViewById(R.id.imgInsert);
        }
    }
}
