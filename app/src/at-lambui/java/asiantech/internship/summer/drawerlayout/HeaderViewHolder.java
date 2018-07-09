package asiantech.internship.summer.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView mImgAvatar;
    private Spinner mSpnEmail;
    private OnClickListener mOnClickListener;

    public CircleImageView getmImgAvatar() {
        return mImgAvatar;
    }

    public Spinner getSpnEmail() {
        return mSpnEmail;
    }

    HeaderViewHolder(@NonNull View itemView, OnClickListener listener) {
        super(itemView);
        mImgAvatar = itemView.findViewById(R.id.imgAvatar);
        mSpnEmail = itemView.findViewById(R.id.spnEmail);
        mImgAvatar.setClickable(true);
        mOnClickListener = listener;
        mImgAvatar.setOnClickListener(view -> mOnClickListener.onGetAvatarClick());
    }

    public CircleImageView getImgAvatar() {
        return mImgAvatar;
    }
}
