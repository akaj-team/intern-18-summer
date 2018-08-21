package asiantech.internship.summer.service.music_player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service.ServiceActivity;
import asiantech.internship.summer.service.utils.TimeTransferUtils;

public class PlayMusicService extends Service {
    private static final int SERVICE_ID = 101;
    private static final String CHANEL_ID = "CHANEL_ID";

    public static final String ACTION_START_PLAY_SONG = "ACTION_START_PLAY_SONG";
    public static final String ACTION_UPDATE_TIME = "ACTION_UPDATE_TIME";
    public static final String ACTION_PAUSE = "ACTION_PAUSE_TIME";
    public static final String ACTION_UN_PAUSE = "ACTION_UN_PAUSE";

    public static final String ACTION_START_SERVICE = "ACTION_START_SERVICE";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";

    public static final String SONG_TITLE = "SONG_TITLE";
    public static final String SONG_DURATION = "SONG_DURATION";
    public static final String CURRENT_TIME = "CURRENT_TIME";

    private MusicPlayer mMusicPlayer;
    private NotificationCompat.Builder mBuilder;
    private RemoteViews mRemoteView;
    private IBinder mBinder = new MusicBinder();

    public void onCreate() {
        super.onCreate();
        mMusicPlayer = new MusicPlayer(getApplicationContext(), new MusicPlayerEventListener() {
            @Override
            public void onPlayerStart(String title, int duration) {
                updatePlay(title);
                Intent startSong = new Intent(ACTION_START_PLAY_SONG);
                startSong.putExtra(SONG_TITLE, title);
                startSong.putExtra(SONG_DURATION, duration);
                sendBroadcast(startSong);
            }

            @Override
            public void onPlayerPlaying(int time) {
                updateTime(TimeTransferUtils.millisecondToClock(time));
                Intent startSong = new Intent(ACTION_UPDATE_TIME);
                startSong.putExtra(CURRENT_TIME, time);
                sendBroadcast(startSong);
            }

            @Override
            public void onPlayerPause() {
                updatePause();
                Intent startSong = new Intent(ACTION_PAUSE);
                sendBroadcast(startSong);
            }

            @Override
            public void onPlayerUnPause() {
                updatePlay();
                Intent startSong = new Intent(ACTION_UN_PAUSE);
                sendBroadcast(startSong);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            switch (Objects.requireNonNull(action)) {
                case ACTION_START_SERVICE:
                    startForegroundService();
                    break;
                case ACTION_PLAY:
                    mMusicPlayer.changeState();
                    break;
                case ACTION_STOP_SERVICE:
                    stopSelf();
                    stopForeground(true);
                    break;
            }
        }
        return START_STICKY;
    }

    private void startForegroundService() {
        mRemoteView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        mRemoteView.setTextViewText(R.id.tvSongTitle, "This is a music player");
        mRemoteView.setTextViewText(R.id.tvTime, "0:00");

        Intent playIntent = new Intent(this, PlayMusicService.class);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0,
                playIntent, 0);
        mRemoteView.setOnClickPendingIntent(R.id.imgPlayOrPause, playPendingIntent);

        Intent deleteIntent = new Intent(this, PlayMusicService.class);
        deleteIntent.setAction(ACTION_STOP_SERVICE);
        PendingIntent deletePendingIntent = PendingIntent.getService(this, 0,
                deleteIntent, 0);
        mRemoteView.setOnClickPendingIntent(R.id.imgClose, deletePendingIntent);

        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setCustomBigContentView(mRemoteView)
                .setSmallIcon(R.drawable.ic_heart_filled)
                .setOngoing(true);
        startForeground(SERVICE_ID, mBuilder.build());
    }

    private void updatePlay(String title) {
        mRemoteView.setTextViewText(R.id.tvSongTitle, title);
        updatePlay();
    }

    private void updatePlay() {
        mRemoteView.setImageViewResource(R.id.imgPlayOrPause, R.drawable.ic_pause);
        mRemoteView.setViewVisibility(R.id.imgClose, View.INVISIBLE);
        changeNotification();
    }

    private void updatePause() {
        mRemoteView.setImageViewResource(R.id.imgPlayOrPause, R.drawable.ic_play);
        mRemoteView.setViewVisibility(R.id.imgClose, View.VISIBLE);
        changeNotification();
    }

    private void updateTime(String time) {
        mRemoteView.setTextViewText(R.id.tvTime, time);
        changeNotification();
    }

    private void changeNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(SERVICE_ID, mBuilder.build());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MusicBinder extends Binder {
        public MusicPlayer getMusicPlayer() {
            return mMusicPlayer;
        }
    }
}
