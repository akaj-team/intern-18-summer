package asiantech.internship.summer.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.model.Employee;
import asiantech.internship.summer.storage.model.Firm;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.EmployeeHolder> {
    private List<Employee> mListEmployee;
    private final boolean mIsEmployee;
    private final Context mContext;
    private final OnItemCLickListener mOnItemCLickListener;
    private List<Firm> mListFirm;
    public final static String ID_FIRM = "id_firm";

    ManageAdapter(List<Employee> mListEmployee, Context mContext, OnItemCLickListener mOnItemCLickListener) {
        this.mListEmployee = mListEmployee;
        this.mIsEmployee = true;
        this.mContext = mContext;
        this.mOnItemCLickListener = mOnItemCLickListener;
    }

    ManageAdapter(Context mContext, List<Firm> mListFirm, OnItemCLickListener mOnItemCLickListener) {
        this.mIsEmployee = false;
        this.mContext = mContext;
        this.mOnItemCLickListener = mOnItemCLickListener;
        this.mListFirm = mListFirm;
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
        if (mIsEmployee) {
            holder.mTvEmployee.setText(mListEmployee.get(position).getmName());
            holder.mImgMenu.setImageResource(R.drawable.ic_delete);
        } else {
            holder.mTvEmployee.setText(mListFirm.get(position).getmName());
            holder.mImgMenu.setImageResource(R.drawable.ic_add);
        }
    }


    @Override
    public int getItemCount() {
        if (mIsEmployee) {
            return mListEmployee.size();
        } else {
            return mListFirm.size();
        }
    }

    class EmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mTvEmployee;
        final ImageView mImgMenu;

        EmployeeHolder(View itemView) {
            super(itemView);
            mTvEmployee = itemView.findViewById(R.id.tvEmployee);
            mImgMenu = itemView.findViewById(R.id.imgMenu);
            mTvEmployee.setOnClickListener(this);
            mImgMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            switch (v.getId()) {
                case R.id.tvEmployee:
                    if (!mIsEmployee) {
                        Intent intent = new Intent(mContext, ListEmployeeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(ID_FIRM, mListFirm.get(position).getmId());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                    else {
                        Toast.makeText(mContext, "You clicked "+mListEmployee.get(position).getmName(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.imgMenu:
                    mOnItemCLickListener.onItemClickListener(position);
                    break;
            }
        }

    }

    interface OnItemCLickListener {
        void onItemClickListener(int position);
    }
}
