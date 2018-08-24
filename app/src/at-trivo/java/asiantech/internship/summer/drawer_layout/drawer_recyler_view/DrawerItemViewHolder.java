package asiantech.internship.summer.drawer_layout.drawer_recyler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.DrawerClickedListener;

public class DrawerItemViewHolder extends RecyclerView.ViewHolder {
    private final ImageView mImgFeatureIcon;
    private final TextView mTvFeatureName;
    private int mFeatureNameId;
    private DrawerClickedListener mListener;

    public ImageView getImgFeatureIcon() {
        return mImgFeatureIcon;
    }

    public TextView getTvFeatureName() {
        return mTvFeatureName;
    }

    public void setFeatureNameId(int featureNameId) {
        this.mFeatureNameId = featureNameId;
        mTvFeatureName.setText(mFeatureNameId);
    }

    DrawerItemViewHolder(View itemView, DrawerClickedListener listener) {
        super(itemView);
        mImgFeatureIcon = itemView.findViewById(R.id.imgFeatureIcon);
        mTvFeatureName = itemView.findViewById(R.id.tvFeatureName);
        mListener = listener;

        itemView.setClickable(true);
        itemView.setOnClickListener(view -> {
            if(mFeatureNameId!=0) {
                mListener.onDrawerItemClicked(mFeatureNameId);
            }
        });
    }
}
