package asiantech.internship.summer.restful.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    final ImageView imgPicture;
    final TextView mTvDescription;

    ImageViewHolder(View itemView) {
        super(itemView);
        imgPicture = itemView.findViewById(R.id.imgPicture);
        mTvDescription = itemView.findViewById(R.id.tvDescription);
    }
}
