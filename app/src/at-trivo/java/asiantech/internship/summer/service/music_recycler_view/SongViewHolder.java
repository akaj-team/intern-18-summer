package asiantech.internship.summer.service.music_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class SongViewHolder extends RecyclerView.ViewHolder {
    private SongInteractListener mListener;
    final TextView mTvTitle;
    final TextView mTvArtist;
    private int mPosition;

    SongViewHolder(View itemView, SongInteractListener listener) {
        super(itemView);
        mListener = listener;
        mTvArtist = itemView.findViewById(R.id.tvArtist);
        mTvTitle = itemView.findViewById(R.id.tvTitle);
        itemView.setOnClickListener(view -> mListener.songClicked(mPosition));
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }
}
