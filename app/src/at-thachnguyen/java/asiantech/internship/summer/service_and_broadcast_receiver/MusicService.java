package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service_and_broadcast_receiver.model.Song;
import de.hdodenhof.circleimageview.CircleImageView;

public class MusicService extends Service implements View.OnClickListener {
    private MediaPlayer mMediaPlayer;
    private final IBinder mMusicBind = new MusicBinder();
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private WeakReference<ImageButton> mImgBtnPrev;
    private WeakReference<ImageButton> mImgBtnPlay;
    private WeakReference<ImageButton> mImgBtnNext;
    private WeakReference<CircleImageView> mImgDisk;
    private WeakReference<TextView> mTvTotalTime;
    private WeakReference<SeekBar> mSeekBarPlay;
    private WeakReference<TextView> mTvTitleSong;
    private WeakReference<TextView> mTvState;
    private WeakReference<TextView> mTvCurrentTime;
    private RotateAnimation mRotateAnimation;
    private RemoteViews mRemoteViews;
    private Handler mHandler;
    private Runnable mRunnable;
    private List<Song> mListSong = new ArrayList<>();
    private int mPosition = 0;
    private static final int ID_NOTI = 1;
    private static final String CHANNEL = "my_channel_01";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Objects.equals(intent.getAction(), getResources().getString(R.string.play_action))) {
            if (mMediaPlayer.isPlaying()) {
                mImgBtnPlay.get().setImageResource(R.drawable.ic_play);
                mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_menu_play_gray);
                mImgDisk.get().clearAnimation();
                mMediaPlayer.pause();
                mNotificationManager.notify(ID_NOTI, mBuilder.build());
            } else {
                mImgBtnPlay.get().setImageResource(R.drawable.ic_pause);
                mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_menu_pause_gray);
                mImgDisk.get().startAnimation(mRotateAnimation);
                mMediaPlayer.start();
                mNotificationManager.notify(ID_NOTI, mBuilder.build());
            }
        } else if (Objects.equals(intent.getAction(), getResources().getString(R.string.close_action))) {
            if (!mMediaPlayer.isPlaying()) {
                stopSelf();
            }
        } else {
            initUI();
            mPosition = intent.getIntExtra(ListenMusicActivity.KEY_POSITION, 0);
            mListSong = Song.getListSong();
            setCurrentSong();
            mImgBtnPlay.get().setOnClickListener(this);
            mImgBtnNext.get().setOnClickListener(this);
            mImgBtnPrev.get().setOnClickListener(this);
            seekBarChangeListener();
            initRotateAnimationDisk();
            setTotalTime();
        }
        return START_STICKY;
    }

    private void setCurrentSong() {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mListSong.get(mPosition).getFile());
        mTvTitleSong.get().setText(mListSong.get(mPosition).getTitle());
        mImgDisk.get().setImageResource(mListSong.get(mPosition).getAvatarSinger());
    }

    private void initUI() {
        mImgBtnPrev = new WeakReference<>(ListenMusicActivity.sImgBtnPrev);
        mImgBtnPlay = new WeakReference<>(ListenMusicActivity.sImgBtnPlay);
        mImgBtnNext = new WeakReference<>(ListenMusicActivity.sImgBtnNext);
        mImgBtnPrev = new WeakReference<>(ListenMusicActivity.sImgBtnPrev);
        mImgDisk = new WeakReference<>(ListenMusicActivity.sImgDisk);
        mTvTotalTime = new WeakReference<>(ListenMusicActivity.sTvTotalTime);
        mSeekBarPlay = new WeakReference<>(ListenMusicActivity.sSeekBarPlay);
        mTvTitleSong = new WeakReference<>(ListenMusicActivity.sTvTitleSong);
        mTvState = new WeakReference<>(ListenMusicActivity.sTvState);
        mTvCurrentTime = new WeakReference<>(ListenMusicActivity.sTvCurrentTime);
    }

    private void seekBarChangeListener() {
        mSeekBarPlay.get().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mImgDisk.get().clearAnimation();
                    mTvState.get().setText(R.string.pause);
                    mImgBtnPlay.get().setImageResource(R.drawable.ic_play);
                    if (mNotificationManager != null) {
                        mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_menu_play_gray);
                        mNotificationManager.notify(ID_NOTI, mBuilder.build());
                    }
                } else {
                    mMediaPlayer.start();
                    mTvState.get().setText(R.string.play);
                    mImgDisk.get().startAnimation(mRotateAnimation);
                    mImgBtnPlay.get().setImageResource(R.drawable.ic_pause);
                    initNotification();
                    if (mNotificationManager != null) {
                        mRemoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_menu_pause_gray);
                        mNotificationManager.notify(ID_NOTI, mBuilder.build());
                    }
                }
                setTotalTime();
                updateCurrentTime();
                break;
            case R.id.imgBtnNext:
                mPosition++;
                if (mPosition > mListSong.size() - 1) {
                    mPosition = 0;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    setCurrentSong();
                    mMediaPlayer.start();
                } else {
                    setCurrentSong();
                }
                setTotalTime();
                updateCurrentTime();
                if (mNotificationManager != null) {
                    setRemoteViews();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                break;
            case R.id.imgBtnPrev:
                mPosition--;
                if (mPosition < 0) {
                    mPosition = mListSong.size() - 1;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    setCurrentSong();
                    mMediaPlayer.start();
                } else {
                    setCurrentSong();
                }
                setTotalTime();
                updateCurrentTime();
                if (mNotificationManager != null) {
                    setRemoteViews();
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                break;
        }
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    private void setTotalTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFomart = new SimpleDateFormat("mm:ss");
        mTvTotalTime.get().setText(timeFomart.format(mMediaPlayer.getDuration()));
        mSeekBarPlay.get().setMax(mMediaPlayer.getDuration());
    }

    private void initRotateAnimationDisk() {
        mRotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(3000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    private void updateCurrentTime() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                mTvCurrentTime.get().setText(timeFormat.format(mMediaPlayer.getCurrentPosition()));
                if (mNotificationManager != null) {
                    mRemoteViews.setTextViewText(R.id.tvCurrentTime, timeFormat.format(mMediaPlayer.getCurrentPosition()));
                    mNotificationManager.notify(ID_NOTI, mBuilder.build());
                }
                mSeekBarPlay.get().setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(mp -> {
                    mPosition++;
                    if (mPosition > mListSong.size() - 1) {
                        mPosition = 0;
                    }
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        setCurrentSong();
                        mMediaPlayer.start();
                    } else {
                        setCurrentSong();
                        mMediaPlayer.start();
                    }
                    if (mNotificationManager != null) {
                        setRemoteViews();
                    }
                    setTotalTime();
                    updateCurrentTime();
                });
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(mRunnable, 100);
    }

    private void setRemoteViews() {
        mRemoteViews.setImageViewResource(R.id.imgSinger, mListSong.get(mPosition).getAvatarSinger());
        mRemoteViews.setTextViewText(R.id.tvTitleSong, mListSong.get(mPosition).getTitle());
    }

    private PendingIntent setOnClick(String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void initNotification() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_music);
        setRemoteViews();
        mRemoteViews.setOnClickPendingIntent(R.id.imgPlay, setOnClick(getResources().getString(R.string.play_action)));
        mRemoteViews.setOnClickPendingIntent(R.id.imgClose, setOnClick(getResources().getString(R.string.close_action)));
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ListenMusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = new NotificationCompat.Builder(this, CHANNEL);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setSmallIcon(R.drawable.ic_stat_music)
                .setCustomBigContentView(mRemoteViews)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL, getResources().getString(R.string.channel_name), importance);
            mNotificationManager.createNotificationChannel(mChannel);
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
        return true;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.pause();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        if (mNotificationManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mNotificationManager.deleteNotificationChannel(CHANNEL);
            }
            mNotificationManager.cancel(ID_NOTI);
        }
        super.onDestroy();
    }
}
