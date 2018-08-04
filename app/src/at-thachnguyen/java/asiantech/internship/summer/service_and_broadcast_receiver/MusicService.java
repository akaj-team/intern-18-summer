package asiantech.internship.summer.service_and_broadcast_receiver;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import asiantech.internship.summer.R;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private final IBinder mMusicBind = new MusicBinder();
    private static final String CHANNEL_ID = "notification";
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private NotificationCompat.Builder mBuilder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (Objects.equals(intent.getAction(), "asiantech.internship.summer")) {
            Log.d("aaa", "onStartCommand: ");
            mMediaPlayer.pause();
//        }
        Log.d("bbb", "onStartCommand: 1");
        return START_STICKY;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        Log.d("bbb", "onCreate: ");
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_music);
        mMediaPlayer = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        mMediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mMusicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        Log.d("ccc", "onUnbind: ");
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("ccc", "onCompletion: ");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
