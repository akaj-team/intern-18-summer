package asiantech.internship.summer.service.music_recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private List<Song> mSongs;
    private SongInteractListener mListener;

    public SongAdapter(List<Song> songs, SongInteractListener listener) {
        this.mSongs = songs;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_song, parent, false);
        return new SongViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mTvTitle.setText(song.getTitle());
        holder.mTvArtist.setText(song.getArtist());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }
}
