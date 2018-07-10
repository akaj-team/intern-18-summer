package asiantech.internship.summer.storage;

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
    private boolean mIsEmployee;

    public EmployeeAdapter(List<String> mListEmployee, boolean mIsEmployee) {
        this.mListEmployee = mListEmployee;
        this.mIsEmployee = mIsEmployee;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_manage, parent, false);
        return new EmployeeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        holder.mTvEmployee.setText(mListEmployee.get(position));
        if(mIsEmployee){
            holder.mImgMenu.setImageResource(R.drawable.ic_delete);
        }
        else{
            holder.mImgMenu.setImageResource(R.drawable.ic_add);
        }
    }

    @Override
    public int getItemCount() {
        return mListEmployee.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {
        TextView mTvEmployee;
        ImageView mImgMenu;
        EmployeeHolder(View itemView) {
            super(itemView);
            mTvEmployee = itemView.findViewById(R.id.tvEmployee);
            mImgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
