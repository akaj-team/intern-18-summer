package asiantech.internship.summer.drawer_layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MenuItem> mMenuItemList;
    private Context mContext;

    public DrawerLayoutAdapter(List<MenuItem> mMenuItemList, Context mContext) {
        this.mMenuItemList = mMenuItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_item, parent, false);
            return new HeaderItemHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new MenuItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position!=0){
            ((MenuItemHolder)holder).mTvTitle.setText(mMenuItemList.get(position-1).getmTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) {
            viewType = 0;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView mImgItem;
        TextView mTvTitle;
        MenuItemHolder(View itemView) {
            super(itemView);
            mImgItem=itemView.findViewById(R.id.imgItem);
            mTvTitle=itemView.findViewById(R.id.tvTitle);
        }
    }

    class HeaderItemHolder extends RecyclerView.ViewHolder {
            CircleImageView mImgAvtar;
            Spinner mSpnMail;
        HeaderItemHolder(View itemView) {
            super(itemView);
            mImgAvtar=itemView.findViewById(R.id.imgAvatar);
            mSpnMail=itemView.findViewById(R.id.spnMail);
        }
    }
}
