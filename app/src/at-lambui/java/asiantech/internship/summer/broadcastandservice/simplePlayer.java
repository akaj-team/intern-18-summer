package asiantech.internship.summer.broadcastandservice;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Space;

import java.io.File;
import java.util.List;

import asiantech.internship.summer.broadcastandservice.model.Song;

public class simplePlayer {
    private OnplayerEventListener mListener;
    private Activity mActivity;
    private List<Song> mListSongs = null;
    private boolean isPaused = false;
    public int mCurrentPosition = 0;
    public long mCurrentDuration = 0;
    private   MediaPlayer mMediaPlayer;
    simplePlayer(Activity activity){
        mActivity = activity;
        mListener = (OnplayerEventListener) mActivity;
    }
    public void init(List<Song> songList){
        mListSongs = songList;
        mCurrentPosition = 0;
        if (mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    nextSong();
                    mListener.onPlayerCompleted();
                }
            });
        }
    }
    public void stop(){
        if (mMediaPlayer != null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }else {
                mMediaPlayer.reset();
            }
        }
    }
    public void pause(){
        if (!isPaused && mMediaPlayer != null){
            mMediaPlayer.pause();
            isPaused = true;
        }
    }
    //play current song
    public void play(){

        if (mMediaPlayer != null){
            if (!isPaused  && !mMediaPlayer.isPlaying()){

                if (mListSongs.size() > 0){
                    Song song = mListSongs.get(mCurrentPosition);
                    try{

                        Log.e("aaa", "vo day");
                       mCurrentDuration = mListSongs.get(mCurrentPosition).getDuration();
                        mMediaPlayer.setDataSource(mListSongs.get(mCurrentPosition).getPath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
//                        mListener.onPlayerStart("Now Playing: "+ mListSongs.get(mCurrentPosition).getArtist() +
//                        "-" + mListSongs.get(mCurrentPosition).getName(),mCurrentDuration);
                    }catch (Exception e){
                        Log.e("XXX",e.getMessage());
                    }
                    mListener.onPlayerStart(song.getName(),(int) (song.getDuration()));
                }
            }else {
                mMediaPlayer.start();
                isPaused = false;
            }
        }
    }
    //play current position
    public void chooseSong(Song song){
        int position = mListSongs.indexOf(song);
        Log.e("aaa","" + position);
        if (mMediaPlayer != null && position > 0 && position <mListSongs.size()){
            if (isPaused){
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mCurrentPosition = position;
            play();
        }
    }

    public void nextSong(){
        if (mMediaPlayer != null){
            if (isPaused){
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            if ((mCurrentPosition + 1) == mListSongs.size()){
                mCurrentPosition = 0;
            }else {
                mCurrentPosition = mCurrentPosition + 1;
            }
            play();
        }
    }
    public void previousSong(){
        if (mMediaPlayer != null){
            if (isPaused){
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            if (mCurrentPosition -1 < 0){
                mCurrentPosition = mListSongs.size();
            }else {
                mCurrentPosition = mCurrentPosition - 1;
            }
            play();
        }
    }
    public void setSeekPosition(int miliseconds){
        if (mMediaPlayer != null){
            mMediaPlayer.seekTo(miliseconds);
        }
    }
    public long getSeekPosition(int position){
        if (mMediaPlayer != null){
            return mListSongs.get(position).getDuration();
        }else {
            return -1;
        }
    }
}
