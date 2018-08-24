package asiantech.internship.summer.broadcastandservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.SeekBar;

import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.broadcastandservice.model.Song;

public class MusicSimplePlayerService extends Service implements OnplayerEventListener {
    private Handler mHandler;
    private Runnable mRunnable;
    private SimplePlayer mSimplePlayer;
    public static final String DURATION_KEY = "Duration_key";
    public static final String DURATION_ACTION = "Duration Action";
    public static final String SONG_ACTION = "Song Action";
    public static final String TITLE_SONG = "Song Title";
    public static final String DURATION_SONG = "Song Duration";
    public static final String UPDATE_TIMER_SONG = "TIME PLAYING";
    public static final String CURRENT_TIME = "TIME CURRENT";
    public static final String ACTION_SERVICE = "ACTION SERVICE";
    public static final String ACTION_PAUSE = "ACTION PAUSE";
    public static final String CLOSE_ACTION = "asiantech.internship.summer.CLOSE_ACTION";
    public static final String PLAY_PAUSE_ACTION = "PLAY PAUSE ACTION";
    public static final String CHANEL_KEY = "KEY CHANEL";
    public static final String ACTION_UN_PAUSE = "ACTION UN PAUSE";
    private Notification mNotification;
    private static final int SERVICE_ID = 111;
    private static final int TIME_DELAY = 500;
    private RemoteViews mNotificationView;
    private NotificationManager mNotificationManager;
    private final IBinder mIbinder = new LocalBinder();

    public MusicSimplePlayerService() {
        if (mSimplePlayer == null) {
            mSimplePlayer = new SimplePlayer();
            mSimplePlayer.setOnplayerEventListener(this);
            mSimplePlayer.play();
        }
        mRunnable = () -> {
            Intent durationIntent = new Intent(DURATION_ACTION);
            durationIntent.putExtra(DURATION_KEY, mSimplePlayer.mCurrentPosition);
            mHandler.postDelayed(mRunnable, TIME_DELAY);
        };
    }

    public void initSong(List<Song> list) {
        mSimplePlayer.init(list);
    }

    public void backSong() {
        mSimplePlayer.previousSong();
    }

    public void nextSong() {
        mSimplePlayer.nextSong();
    }

    public void pauseSong() {
        mSimplePlayer.pause();
    }

    public void playSong() {
        mSimplePlayer.play();
    }

    public void chooseSong(Song song) {
        mSimplePlayer.chooseSong(song);
    }

    public void setProgessSeekbar(SeekBar seekbar) {
        mSimplePlayer.setSeekPosition(seekbar.getProgress());
    }

    public class LocalBinder extends Binder {
        MusicSimplePlayerService getservice() {
            return MusicSimplePlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIbinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            switch (Objects.requireNonNull(action)) {
                case ACTION_SERVICE:
                    initNotification();
                    break;
                case PLAY_PAUSE_ACTION:
                    mSimplePlayer.updateCharacter();
                    if (mSimplePlayer.isPlayingSong()) {
                        mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_pause);
                    } else {
                        mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_play);
                    }
                    updateState();
                    break;
                case CLOSE_ACTION:
                    mNotificationManager.cancel(SERVICE_ID);
                    stopSelf();
                    stopForeground(true);
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onPauseSong() {
        mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_play);
        mNotificationManager.notify(SERVICE_ID, mNotification);
        Intent pauseSong = new Intent(ACTION_PAUSE);
        sendBroadcast(pauseSong);
    }

    @Override
    public void onUnPauseSong() {
        if (mNotificationManager != null) {
            mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_pause);
            mNotificationManager.notify(SERVICE_ID, mNotification);
            Intent unPauseSong = new Intent(ACTION_UN_PAUSE);
            sendBroadcast(unPauseSong);
        }
    }

    @Override
    public void onPlayerPlaying(long time) {
        updateTimer((int) time);
        Intent startSong = new Intent(UPDATE_TIMER_SONG);
        startSong.putExtra(CURRENT_TIME, time);
        sendBroadcast(startSong);
    }

    @Override
    public void onPlayerStart(String title, int duration) {
        updateName(title);
        updateState();
        Intent startSong = new Intent(SONG_ACTION);
        startSong.putExtra(TITLE_SONG, title);
        startSong.putExtra(DURATION_SONG, duration);
        sendBroadcast(startSong);
    }

    private void initNotification() {

        //intent initservice
        Intent intentService = new Intent(this, BroadCastReceiverAndServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentService,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent closeIntent = new Intent(getBaseContext(), MusicSimplePlayerService.class);
        closeIntent.setAction(CLOSE_ACTION);
        PendingIntent closePendingIntent = PendingIntent.getService(this, SERVICE_ID, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //intent khi play nhạc or pause thì update trên main activity
        Intent playIntent = new Intent(getBaseContext(), MusicSimplePlayerService.class);
        playIntent.setAction(PLAY_PAUSE_ACTION);
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationView = new RemoteViews(getPackageName(), R.layout.remote_view_notification_player);
        mNotificationView.setOnClickPendingIntent(R.id.imgCloseNotification, closePendingIntent);
        mNotificationView.setOnClickPendingIntent(R.id.imgPlayNotification, playPendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_KEY);

        intentService.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(mNotificationView)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);

        Notification notification = builder.build();
        //luu lai thong bao, cho thong bao den bo phan con, biet ma cap nhat
        mNotification = notification;
        notification.flags |= Notification.FLAG_NO_CLEAR;

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(SERVICE_ID, notification);
        }
    }

    private void updateState() {
        if (mNotificationManager != null) {
            if (!mSimplePlayer.isPlayingSong()) {
                mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_play);
            } else {
                mNotificationView.setImageViewResource(R.id.imgPlayNotification, R.drawable.ic_pause);
            }
            mNotificationManager.notify(SERVICE_ID, mNotification);
        }
    }

    private void updateName(String title) {
        if (mNotificationManager != null) {
            mNotificationView.setTextViewText(R.id.tvNameSongNotification, title);
            mNotificationManager.notify(SERVICE_ID, mNotification);
        }
    }

    private void updateTimer(int time) {
        if (mNotificationManager != null) {
            mNotificationView.setTextViewText(R.id.tvTimerUpdate, GetDurationTransfer.getDuration(time));
            mNotificationManager.notify(SERVICE_ID, mNotification);
        }
    }

    public void updateDefault() {
        Song song = mSimplePlayer.mListSongs.get(mSimplePlayer.mCurrentPosition);
        onPlayerStart(song.getName(), (int) song.getDuration());
        updateState();
    }

    public boolean updateStateDefault() {
        return mSimplePlayer.isPlayingSong();
    }

}
