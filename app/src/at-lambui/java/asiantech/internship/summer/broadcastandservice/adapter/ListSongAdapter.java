package asiantech.internship.summer.broadcastandservice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.broadcastandservice.OnClickListenerSong;
import asiantech.internship.summer.broadcastandservice.getDurationTransfer;
import asiantech.internship.summer.broadcastandservice.model.Song;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongViewHolder> {
    private List<Song> mSongsList;
    private Context mContext;
    private OnClickListenerSong mOnClickedSong;

    public ListSongAdapter(List<Song> mSongsList,OnClickListenerSong onClickedSong, Context mContext) {
        this.mSongsList = mSongsList;
        this.mContext = mContext;
        this.mOnClickedSong = onClickedSong;
    }

    @NonNull
    @Override
    public ListSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewholder_song,parent,false);
        return new ListSongViewHolder(itemView,mSongsList,mOnClickedSong);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSongViewHolder holder, int position) {
        Song song = mSongsList.get(position);
        if (song != null){
            Log.e("xxxx", " " + getDurationTransfer.getDuration((int)(song.getDuration())));
            holder.getTvNameSong().setText(song.getName());
            holder.getTvArtist().setText(song.getArtist());
        }
    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }
}
