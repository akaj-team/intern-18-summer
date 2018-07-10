package asiantech.internship.summer.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class MenuItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImgItemMenu;
    private TextView mTvItemMenu;
    private int mPosition;
    private OnClickListener mOnClickListener;

    //null listener , truyen listener sau khi khai bao o adapter
    MenuItemViewHolder(@NonNull View itemView, OnClickListener listener) {
        super(itemView);
        mOnClickListener = listener;

        mImgItemMenu = itemView.findViewById(R.id.imgItemMenu);
        mTvItemMenu = itemView.findViewById(R.id.tvItemMenu);
        RelativeLayout rlItemView = itemView.findViewById(R.id.rlItemView);
        rlItemView.setOnClickListener(view -> mOnClickListener.onToastClickItem(mPosition));
    }

    public void setPosition(int Position) {
        this.mPosition = Position;
    }

    public ImageView getImgItemMenu() {
        return mImgItemMenu;
    }

    public TextView getTvItemMenu() {
        return mTvItemMenu;
    }
}

