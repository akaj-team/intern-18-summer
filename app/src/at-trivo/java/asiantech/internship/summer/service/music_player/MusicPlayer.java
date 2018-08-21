package asiantech.internship.summer.service.music_player;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import asiantech.internship.summer.service.model.Song;

public class MusicPlayer {
    private static final String TAG = MusicPlayer.class.getSimpleName();

    private MusicPlayerEventListener mListener;
    private Context mContext;

    private List<Song> mSongs = null;
    private boolean mIsPaused = false;
    private int mCurrentPosition = 0;
    private MediaPlayer mMediaPlayer;
    private TimeUpdater mTimeUpdater;

    MusicPlayer(Context context, MusicPlayerEventListener listener) {
        mContext = context;
        mListener = listener;
    }

    /**
     * init list song and media player
     *
     * @param songs input songs
     */
    public void init(List<Song> songs) {
        mSongs = songs;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mediaPlayer -> {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                nextSong();
            });
        }
    }

    /**
     * change state of player from pause to playing and reverse
     */
    public void changeState() {
        if (mIsPaused || !mMediaPlayer.isPlaying()) {
            play();
        } else {
            pause();
        }
    }

    private void pause() {
        if (!mIsPaused && mMediaPlayer != null) {
            mMediaPlayer.pause();
            mIsPaused = true;
            endNotifyTime();
        }
        mListener.onPlayerPause();
    }

    private void play() {
        if (mMediaPlayer != null) {
            if (!mIsPaused && !mMediaPlayer.isPlaying()) {
                if (mSongs != null && mSongs.size() > 0) {
                    Song song = mSongs.get(mCurrentPosition);
                    long id = song.getId();
                    Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    try {
                        mMediaPlayer.setDataSource(mContext, trackUri);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    mListener.onPlayerStart(song.getTitle(), mMediaPlayer.getDuration());
                }
            } else {
                mMediaPlayer.start();
                mListener.onPlayerUnPause();
            }
            mIsPaused = false;
            mTimeUpdater = new TimeUpdater();
            mTimeUpdater.start();
        }
    }

    /**
     * play song at particular position
     *
     * @param position position of song in list song
     */
    public void playAt(int position) {
        if (mMediaPlayer != null && position >= 0 && position < mSongs.size()) {
            mTimeUpdater.stopUpdate();
            if (mIsPaused) {
                mIsPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mCurrentPosition = position;
            play();
        }
    }

    /**
     * play next song in list song
     */
    public void nextSong() {
        if (mMediaPlayer != null) {
            if (mTimeUpdater != null) {
                mTimeUpdater.stopUpdate();
            }
            if (mIsPaused) {
                mIsPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mCurrentPosition = (mCurrentPosition + 1) % mSongs.size();
            play();
        }
    }

    /**
     * play previous song in list song
     */
    public void previousSong() {
        if (mMediaPlayer != null) {
            mTimeUpdater.stopUpdate();
            if (mIsPaused) {
                mIsPaused = false;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mCurrentPosition--;
            if (mCurrentPosition < 0) {
                mCurrentPosition += mSongs.size();
            }
            play();
        }
    }

    /**
     * seek playing song to a particular millisecond
     *
     * @param millisecond time to seek to
     */
    public void setSeekPosition(int millisecond) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(millisecond);
            mTimeUpdater = new TimeUpdater();
            mTimeUpdater.start();
        }
    }

    /**
     * @return playing song's time in millisecond
     */
    private int getSeekPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * stop notify playing song's time
     */
    public void endNotifyTime() {
        if (mTimeUpdater != null) {
            mTimeUpdater.stopUpdate();
        }
    }

    /**
     * notify song's information including name and duration
     */
    public void transferPlayingSongInfo() {
        if (mMediaPlayer != null) {
            mListener.onPlayerStart(mSongs.get(mCurrentPosition).getTitle(),
                    mMediaPlayer.getDuration());
        }
    }

    private class TimeUpdater extends Thread {
        boolean isRun;

        TimeUpdater() {
            isRun = true;
        }

        public void run() {
            while (isRun) {
                mListener.onPlayerPlaying(getSeekPosition());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        void stopUpdate() {
            isRun = false;
        }
    }
}
