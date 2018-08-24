package asiantech.internship.summer.broadcastandservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.broadcastandservice.OnClickListenerSong;
import asiantech.internship.summer.broadcastandservice.model.Song;

public class ListSongViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvNameSong;
    private TextView mTvArtist;
    private int mPosition;
    private OnClickListenerSong mOnClickedSong;
    private List<Song> mListSongs;

    ListSongViewHolder(View itemView, List<Song> listSongs, OnClickListenerSong onClickListenerSong) {
        super(itemView);
        this.mOnClickedSong = onClickListenerSong;
        this.mListSongs = listSongs;
        mTvNameSong = itemView.findViewById(R.id.tvNameSong);
        mTvArtist = itemView.findViewById(R.id.tvArtist);
        itemView.setOnClickListener(view -> mOnClickedSong.onSongClicked(mListSongs.get(getAdapterPosition())));
    }

    public TextView getTvNameSong() {
        return mTvNameSong;
    }

    public TextView getTvArtist() {
        return mTvArtist;
    }
}