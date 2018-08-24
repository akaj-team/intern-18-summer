package asiantech.internship.summer.drawer_layout.drawer_recyler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.DrawerClickedListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerHeaderViewHolder extends RecyclerView.ViewHolder {

    private final CircleImageView mCircleImgAvatar;
    private final Spinner mSpnEmailChoice;
    private DrawerClickedListener mListener;

    public CircleImageView getCircleImgAvatar() {
        return mCircleImgAvatar;
    }

    public Spinner getSpnEmailChoice() {
        return mSpnEmailChoice;
    }

    DrawerHeaderViewHolder(View itemView, DrawerClickedListener listener) {
        super(itemView);
        mListener = listener;
        mCircleImgAvatar = itemView.findViewById(R.id.circleImgAvatar);
        mSpnEmailChoice = itemView.findViewById(R.id.spnEmailChoice);
        mCircleImgAvatar.setOnClickListener(view -> mListener.onDrawerHeaderImageClicked());
    }
}
