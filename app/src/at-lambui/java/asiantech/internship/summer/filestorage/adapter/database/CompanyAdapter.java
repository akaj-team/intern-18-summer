package asiantech.internship.summer.filestorage.adapter.database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.DatabaseActivity;
import asiantech.internship.summer.filestorage.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter {
    private List<Company> mCompanyList;
    private DatabaseActivity.AddOjectListener mListener;

    public CompanyAdapter(List<Company> mCompanyList, DatabaseActivity.AddOjectListener listener) {
        this.mCompanyList = mCompanyList;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_company, viewGroup, false);
        return new CompanyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindViewHolder(((CompanyViewHolder) holder), mCompanyList.get(position));
    }

    private void onBindViewHolder(CompanyViewHolder companyViewHolder, Company company) {
        companyViewHolder.companyId = company.getId();
        companyViewHolder.getTvCompanyName().setText(company.getName());
        companyViewHolder.getTvCompanyAddress().setText(company.getAddress());
    }

    @Override
    public int getItemCount() {
        return mCompanyList.size();
    }
}
