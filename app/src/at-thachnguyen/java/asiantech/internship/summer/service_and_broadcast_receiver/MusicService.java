package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
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
    private List<Song> mListSong = new ArrayList<>();
    private int mPosition = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Objects.equals(intent.getAction(), "play")) {
            if (mMediaPlayer.isPlaying()) {
                mImgBtnPlay.get().setImageResource(R.drawable.ic_play);
                mRemoteViews.setImageViewResource(R.id.imgPause, R.drawable.ic_play_gray);
                mImgDisk.get().clearAnimation();
                mMediaPlayer.pause();
                mNotificationManager.notify(0, mBuilder.build());
            } else {
                mImgBtnPlay.get().setImageResource(R.drawable.ic_pause);
                mRemoteViews.setImageViewResource(R.id.imgPause, R.drawable.ic_pause_gray);
                mImgDisk.get().startAnimation(mRotateAnimation);
                mMediaPlayer.start();
                mNotificationManager.notify(0, mBuilder.build());
            }
        } else {
            initUI();
            addListSong();
            setCurrentSong();
            mImgBtnPlay.get().setOnClickListener(this);
            mImgBtnNext.get().setOnClickListener(this);
            mImgBtnPrev.get().setOnClickListener(this);
            seekBarChangeListener();
            initRotateAnimationDisk();
            setTotalTime();
            initNotification();
        }
        return START_STICKY;
    }

    @Override

    public void onCreate() {
        super.onCreate();
    }

    private void setCurrentSong() {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mListSong.get(mPosition).getmFile());
        mTvTitleSong.get().setText(mListSong.get(mPosition).getmTitle());
        mImgDisk.get().setImageResource(mListSong.get(mPosition).getmAvatarSinger());
    }

    private void initUI() {
        mImgBtnPrev = new WeakReference<>(ServiceBroadCastActivity.sImgBtnPrev);
        mImgBtnPlay = new WeakReference<>(ServiceBroadCastActivity.sImgBtnPlay);
        mImgBtnNext = new WeakReference<>(ServiceBroadCastActivity.sImgBtnNext);
        mImgBtnPrev = new WeakReference<>(ServiceBroadCastActivity.sImgBtnPrev);
        mImgDisk = new WeakReference<>(ServiceBroadCastActivity.sImgDisk);
        mTvTotalTime = new WeakReference<>(ServiceBroadCastActivity.sTvTotalTime);
        mSeekBarPlay = new WeakReference<>(ServiceBroadCastActivity.sSeekBarPlay);
        mTvTitleSong = new WeakReference<>(ServiceBroadCastActivity.sTvTitleSong);
        mTvState = new WeakReference<>(ServiceBroadCastActivity.sTvState);
        mTvCurrentTime = new WeakReference<>(ServiceBroadCastActivity.sTvCurrentTime);
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
                } else {
                    mMediaPlayer.start();
                    mTvState.get().setText(R.string.play);
                    mImgDisk.get().startAnimation(mRotateAnimation);
                    mImgBtnPlay.get().setImageResource(R.drawable.ic_pause);
                    mNotificationManager.notify(0, mBuilder.build());
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
                setRemoteViews();
                mNotificationManager.notify(0, mBuilder.build());
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
                setRemoteViews();
                mNotificationManager.notify(0, mBuilder.build());
                setTotalTime();
                updateCurrentTime();
                break;
        }
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    private void addListSong() {
        mListSong = new ArrayList<>();
        mListSong.add(new Song("Đừng như thói quen", R.raw.dung_nhu_thoi_quen, R.drawable.img_avt_jaykii_and_sara));
        mListSong.add(new Song("Cuộc sống em ổn không", R.raw.cuoc_song_em_on_khong, R.drawable.img_avt_anh_tu));
        mListSong.add(new Song("Đừng quên tên anh", R.raw.dung_quen_ten_anh, R.drawable.img_avt_hoa_vinh));
        mListSong.add(new Song("Lỡ thương một người", R.raw.lo_thuong_mot_nguoi, R.drawable.img_avt_nguyen_dinh_vu));
        mListSong.add(new Song("Rồi người thương cũng hóa người dưng", R.raw.roi_nguoi_thuong_cung_hoa_nguoi_dung, R.drawable.img_avt_hien_ho));
        mListSong.add(new Song("Sai người sai thời điểm", R.raw.sai_nguoi_sai_thoi_diem, R.drawable.img_avt_thanh_hung));
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
        mRotateAnimation.setDuration(2000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    private void updateCurrentTime() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                mTvCurrentTime.get().setText(timeFormat.format(mMediaPlayer.getCurrentPosition()));
                mRemoteViews.setTextViewText(R.id.tvCurrentTime, timeFormat.format(mMediaPlayer.getCurrentPosition()));
                mNotificationManager.notify(0, mBuilder.build());
                mSeekBarPlay.get().setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(mp -> {
                    mPosition++;
                    if (mPosition > mListSong.size() - 1) {
                        mPosition = 0;
                    }
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer.start();
                    } else {
                        setCurrentSong();
                    }
                    setRemoteViews();
                    mNotificationManager.notify(0, mBuilder.build());
                    setTotalTime();
                    updateCurrentTime();
                });
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable, 100);
    }

    private void setRemoteViews() {
        mRemoteViews.setImageViewResource(R.id.imgSinger, mListSong.get(mPosition).getmAvatarSinger());
        mRemoteViews.setTextViewText(R.id.tvTitleSong, mListSong.get(mPosition).getmTitle());
    }

    private PendingIntent setOnClick(int requestCode,String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(getBaseContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void initNotification() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_music);
        setRemoteViews();
        mRemoteViews.setOnClickPendingIntent(R.id.imgPause, setOnClick(0,"play"));
//        mRemoteViews.setOnClickPendingIntent(R.id.imgPause, setOnClick(1,"close"));
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ServiceBroadCastActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = new NotificationCompat.Builder(this, "0");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setSmallIcon(R.drawable.ic_music)
                .setCustomBigContentView(mRemoteViews)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMusicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return false;
    }
}
