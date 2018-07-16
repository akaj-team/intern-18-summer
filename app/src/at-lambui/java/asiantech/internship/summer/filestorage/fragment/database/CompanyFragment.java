package asiantech.internship.summer.filestorage.fragment.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import asiantech.internship.summer.R;

public class CompanyFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerViewListCompany;
    private Toolbar  mActionBarToolbar;
    private ImageView mImgAdd,mImgDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company,container,false);
        initViews(view);



        mImgAdd.setOnClickListener(this);
        mImgDelete.setOnClickListener(this);




        return view;

    }

    private void initViews(View view) {
        mRecyclerViewListCompany = view.findViewById(R.id.recyclerViewCompany);
        mActionBarToolbar = getActivity().findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("List Company");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Add:


        }


    }
}
