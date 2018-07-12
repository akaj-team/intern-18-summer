package asiantech.internship.summer.storage.adpater.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.storage.model.database.models.Company;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.MyViewHolder> {
    private Context mContext;
    private List<Company> mCompanyList;

    public DatabaseAdapter(Context context, List<Company> companyList) {
        this.mContext = context;
        this.mCompanyList = companyList;
    }

    @NonNull
    @Override
    public DatabaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_company, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseAdapter.MyViewHolder holder, int position) {
        Company company = mCompanyList.get(position);
        holder.tvCompanyName.setText(company.getmNameCompany());
    }

    @Override
    public int getItemCount() {
        return mCompanyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCompanyName;

        MyViewHolder(View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        }
    }
}
