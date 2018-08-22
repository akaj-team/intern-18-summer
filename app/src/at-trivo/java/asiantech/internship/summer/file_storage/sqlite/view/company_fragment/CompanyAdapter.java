package asiantech.internship.summer.file_storage.sqlite.view.company_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.sqlite.database.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> {

    private List<Object> mCompanies;
    private CompanyFragment.OnFragmentInteractionListener mListener;

    CompanyAdapter(List<Object> companies, CompanyFragment.OnFragmentInteractionListener listener) {
        mCompanies = companies;
        mListener = listener;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_company, parent, false);
        return new CompanyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = (Company) mCompanies.get(position);
        holder.getTvCompanyAddress().setText(company.getAddress());
        holder.getTvCompanyName().setText(company.getName());
        holder.setCompanyId(company.getId());
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }
}
