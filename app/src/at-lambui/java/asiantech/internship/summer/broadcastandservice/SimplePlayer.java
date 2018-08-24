package asiantech.internship.summer.broadcastandservice;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import asiantech.internship.summer.broadcastandservice.model.Song;

public class SimplePlayer {
    private static final int TIME_DELAY = 1000;
    private OnplayerEventListener mListener;
    private Activity mActivity;
     List<Song> mListSongs = null;
    private boolean isPaused = false;
    public int mCurrentPosition = 0;
    private MediaPlayer mMediaPlayer;
    private Runnable mRunnable;
    private Handler mHandler = new Handler();
    public void init(List<Song> songList) {
        mListSongs = songList;
        mCurrentPosition = 0;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.stop();
                mediaPlayer.reset();
                nextSong();
                mListener.onPauseSong();
            });
        }
    }

    public void setOnplayerEventListener(OnplayerEventListener listener) {
        mListener = listener;
    }

    public void stop() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            } else {
                mMediaPlayer.reset();
            }
        }
    }

    public void pause() {
        if (!isPaused && mMediaPlayer != null) {
            mMediaPlayer.pause();
            isPaused = true;
        }
        mListener.onPauseSong();
    }

    public void play() {

        if (mMediaPlayer != null) {
            if (!isPaused && !mMediaPlayer.isPlaying()) {

                if (mListSongs.size() > 0) {
                    Song song = mListSongs.get(mCurrentPosition);
                    try {
                        long currentDuration = mListSongs.get(mCurrentPosition).getDuration();
                        mMediaPlayer.setDataSource(mListSongs.get(mCurrentPosition).getPath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (Exception e) {
                        Log.e("XXX", e.getMessage());
                    }
                    mListener.onPlayerStart(song.getName(), (int) (song.getDuration()));
                }
            } else {
                mMediaPlayer.start();
                isPaused = false;
                mListener.onUnPauseSong();
            }
            mHandler.removeCallbacks(updateSeekBar);
            mHandler.postDelayed(updateSeekBar, TIME_DELAY);
        }
    }

    //play current position
    public void chooseSong(Song song) {
        int position = mListSongs.indexOf(song);
        if (mMediaPlayer != null && position > 0 && position < mListSongs.size()) {

            if (isPaused) {
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mCurrentPosition = position;
            play();
        }
    }

    public void nextSong() {
        if (mMediaPlayer != null) {
            if (isPaused) {
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            if ((mCurrentPosition + 1) == mListSongs.size()) {
                mCurrentPosition = 0;
            } else {
                mCurrentPosition = mCurrentPosition + 1;
            }
            play();
        }
    }

    public void previousSong() {
        if (mMediaPlayer != null) {

            if (isPaused) {
                isPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            if (mCurrentPosition - 1 < 0) {
                mCurrentPosition = mListSongs.size();
            } else {
                mCurrentPosition = mCurrentPosition - 1;
            }
            play();
        }
    }

    public void updateCharacter() {
        if (!mMediaPlayer.isPlaying()) {
            play();
        } else {
            pause();
        }
    }

    public boolean isPlayingSong() {

        return mMediaPlayer.isPlaying();
    }

    public void setSeekPosition(int miliseconds) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(miliseconds);
        }
    }

    private long getSeekPosition() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return -1;
        }
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer.isPlaying()) {
                mListener.onPlayerPlaying(getSeekPosition());
            }
            mHandler.postDelayed(this, 500);
        }
    };
}
