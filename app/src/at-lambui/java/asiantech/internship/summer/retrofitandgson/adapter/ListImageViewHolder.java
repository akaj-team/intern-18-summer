package asiantech.internship.summer.retrofitandgson.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import asiantech.internship.summer.R;

public class ListImageViewHolder extends RecyclerView.ViewHolder {
    final ImageView imgPhoto;

    ListImageViewHolder(View itemView) {
        super(itemView);
        imgPhoto = itemView.findViewById(R.id.imgPhoto);
    }

}
