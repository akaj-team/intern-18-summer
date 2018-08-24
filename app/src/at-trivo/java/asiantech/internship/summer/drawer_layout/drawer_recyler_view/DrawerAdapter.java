package asiantech.internship.summer.drawer_layout.drawer_recyler_view;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.DrawerClickedListener;
import asiantech.internship.summer.drawer_layout.model.DrawerHeader;
import asiantech.internship.summer.drawer_layout.model.DrawerItem;

public class DrawerAdapter extends RecyclerView.Adapter {

    private List<Object> mDataSet;
    private Activity mActivity;
    private DrawerClickedListener mListener;
    private static final int VIEW_TYPE_DRAWER_HEADER = 0;
    private static final int VIEW_TYPE_DRAWER_ITEM = 1;

    public DrawerAdapter(List<Object> dataSet, Activity activity,DrawerClickedListener listener) {
        this.mDataSet = dataSet;
        mActivity = activity;
        mListener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_DRAWER_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_drawer_header, parent, false);
                return new DrawerHeaderViewHolder(view,mListener);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_drawer_item, parent, false);
                return new DrawerItemViewHolder(view,mListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Object object = mDataSet.get(position);
        switch (type) {
            case VIEW_TYPE_DRAWER_HEADER:
                onBindDrawerHeaderViewHolder((DrawerHeaderViewHolder) holder, (DrawerHeader) object);
                break;
            default:
                onBindDrawerItemViewHolder((DrawerItemViewHolder) holder, (DrawerItem) object);
        }
    }

    private void onBindDrawerItemViewHolder(@NonNull DrawerItemViewHolder holder, DrawerItem item) {
        holder.getImgFeatureIcon().setImageResource(item.getImageId());
        holder.setFeatureNameId(item.getTitleId());
        int color = item.isSelected()?mActivity.getResources().getColor(R.color.colorJava): Color.BLACK;
        holder.getTvFeatureName().setTextColor(color);
        ImageViewCompat.setImageTintList(holder.getImgFeatureIcon(), ColorStateList.valueOf(color));
    }

    private void onBindDrawerHeaderViewHolder(@NonNull DrawerHeaderViewHolder holder, DrawerHeader item) {
        String[] emailList = mActivity.getResources().getStringArray(item.getEmailArrayId());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mActivity, R.layout.support_simple_spinner_dropdown_item, emailList);
        holder.getSpnEmailChoice().setAdapter(arrayAdapter);
        if (item.getUri() == null) {
            holder.getCircleImgAvatar().setImageResource(item.getAvatarId());
        } else {
            Glide.with(mActivity).load(item.getUri()).into(holder.getCircleImgAvatar());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mDataSet.get(position);
        if (object instanceof DrawerItem) {
            return VIEW_TYPE_DRAWER_ITEM;
        }
        return VIEW_TYPE_DRAWER_HEADER;
    }
}
