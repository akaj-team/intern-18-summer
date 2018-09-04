package asiantech.internship.summer.service_and_broadcast_receiver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service_and_broadcast_receiver.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private final List<Song> mListSongs;
    private final Context mContext;
    private final OnChooseSongListener mOnChooseSongListener;

    SongAdapter(List<Song> mListSongs, Context mContext, OnChooseSongListener mOnChooseSongListener) {
        this.mListSongs = mListSongs;
        this.mContext = mContext;
        this.mOnChooseSongListener = mOnChooseSongListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_song, parent, false);
        return new SongHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongHolder holder, int position) {
        holder.mImgSinger.setImageResource(mListSongs.get(position).getAvatarSinger());
        holder.mTvTitleSong.setText(mListSongs.get(position).getTitle());
        holder.mTvSinger.setText(mListSongs.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return mListSongs.size();
    }

    class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImgSinger;
        private final TextView mTvTitleSong;
        private final TextView mTvSinger;

        SongHolder(View itemView) {
            super(itemView);
            mImgSinger = itemView.findViewById(R.id.imgSinger);
            mTvTitleSong = itemView.findViewById(R.id.tvTitleSong);
            mTvSinger = itemView.findViewById(R.id.tvSinger);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnChooseSongListener.onChooseSongListener(getLayoutPosition());
        }
    }

    interface OnChooseSongListener {
        void onChooseSongListener(int position);
    }
}
