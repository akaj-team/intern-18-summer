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
import asiantech.internship.summer.storage.model.database.models.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Company> mCompanyList;
    private OnImageItemClickListener mListener;

    public CompanyAdapter(Context context, ArrayList<Company> companyList, OnImageItemClickListener listener) {
        this.mContext = context;
        this.mCompanyList = companyList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CompanyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_company, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.MyViewHolder holder, int position) {
        Company company = mCompanyList.get(position);
        holder.tvCompanyName.setText(company.getNameCompany());
    }

    @Override
    public int getItemCount() {
        return mCompanyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCompanyName;
        private final ImageView imgViewEmployee;

        MyViewHolder(View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            imgViewEmployee = itemView.findViewById(R.id.imgViewEmployee);
            imgViewEmployee.setOnClickListener(view -> mListener.onImageAddClick(mCompanyList.get(getAdapterPosition()).getIdCompany()));
        }
    }
}
