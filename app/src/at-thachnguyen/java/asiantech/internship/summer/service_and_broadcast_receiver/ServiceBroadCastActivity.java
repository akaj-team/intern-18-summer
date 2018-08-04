package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service_and_broadcast_receiver.model.Song;
import asiantech.internship.summer.view_and_view_group.ViewActivity;

@SuppressLint("Registered")
public class ServiceBroadCastActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImgDisk;
    private SeekBar mSeekBarPlay;
    private TextView mTvCurrentTime;
    private TextView mTvState;
    private TextView mTvTotalTime;
    private TextView mTvTitleSong;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnNext;
    private RotateAnimation mRotateAnimation;
    private MediaPlayer mMediaPlayer;
    private List<Song> mListSong;
    private MusicService mMusicService;
    private Intent mPlayIntent;
    private boolean mMusicBound = false;
    private ServiceConnection mServiceConnection;
    private int mPosition = 0;
    private static final String CHANNEL_ID = "notification";
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_broad_cast);
        initView();
        addListSong();
        initMediaPlayer();
        initRotateAnimationDisk();
        mImgBtnPlay.setOnClickListener(this);
        mImgBtnNext.setOnClickListener(this);
        mImgBtnPrev.setOnClickListener(this);
        initServiceConnection();
        seekBarChangeListener();
        initNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(this, MusicService.class);
            bindService(mPlayIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
            startService(mPlayIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, MusicService.class));
    }

    private void initView() {
        mImgDisk = findViewById(R.id.imgDisk);
        mTvCurrentTime = findViewById(R.id.tvCurrentTime);
        mTvState = findViewById(R.id.tvState);
        mTvTotalTime = findViewById(R.id.tvTotalTime);
        mTvTitleSong = findViewById(R.id.tvTitleSong);
        mImgBtnPrev = findViewById(R.id.imgBtnPrev);
        mImgBtnPlay = findViewById(R.id.imgBtnPlay);
        mImgBtnNext = findViewById(R.id.imgBtnNext);
        mSeekBarPlay = findViewById(R.id.seekBarPlay);
    }

    private void addListSong() {
        mListSong = new ArrayList<>();
        mListSong.add(new Song("Cuộc sống em ổn không", R.raw.cuoc_song_em_on_khong, R.drawable.img_avt_anh_tu));
        mListSong.add(new Song("Đừng như thói quen", R.raw.dung_nhu_thoi_quen, R.drawable.img_avt_jaykii_and_sara));
        mListSong.add(new Song("Đừng quên tên anh", R.raw.dung_quen_ten_anh, R.drawable.img_avt_hoa_vinh));
        mListSong.add(new Song("Lỡ thương một người", R.raw.lo_thuong_mot_nguoi, R.drawable.img_avt_nguyen_dinh_vu));
        mListSong.add(new Song("Rồi người thương cũng hóa người dưng", R.raw.roi_nguoi_thuong_cung_hoa_nguoi_dung, R.drawable.img_avt_hien_ho));
        mListSong.add(new Song("Sai người sai thời điểm", R.raw.sai_nguoi_sai_thoi_diem, R.drawable.img_avt_thanh_hung));
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(ServiceBroadCastActivity.this, mListSong.get(mPosition).getmFile());
        mTvTitleSong.setText(mListSong.get(mPosition).getmTitle());
    }

    private void initRotateAnimationDisk() {
        mRotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(2000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    private void initServiceConnection() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                mMusicService = binder.getService();
                // mMusicService.setListSongs(mListSong);
                mMusicBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMusicBound = false;
            }
        };
    }

    private void initRemoteViews() {
        mRemoteViews.setImageViewResource(R.id.imgSinger, mListSong.get(mPosition).getmAvatarSinger());
        mRemoteViews.setTextViewText(R.id.tvTitleSong, mListSong.get(mPosition).getmTitle());
    }

    private void initNotification() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_music);
        initRemoteViews();
        Intent pauseButton = new Intent(this, MusicService.class);
        pauseButton.setAction("asiantech.internship.summer");
        PendingIntent pendingPauseIntent = PendingIntent.getService(getBaseContext(), 0, pauseButton, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.imgBtnPause, pendingPauseIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, ViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = new NotificationCompat.Builder(this, "0");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setSmallIcon(R.drawable.ic_music)
                .setCustomBigContentView(mRemoteViews);

//                                .setContentIntent(pendingIntent);

    }

    private void setIcon() {
        if (mMediaPlayer.isPlaying()) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause);
        }
        else {
            mImgBtnPlay.setImageResource(R.drawable.ic_play);
        }
    }

    private void seekBarChangeListener() {
        mSeekBarPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMediaPlayer.seekTo(progress);
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

    private void setTotalTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFomart = new SimpleDateFormat("mm:ss");
        mTvTotalTime.setText(timeFomart.format(mMediaPlayer.getDuration()));
        mSeekBarPlay.setMax(mMediaPlayer.getDuration());
    }

    private void updateCurrentTime() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                mTvCurrentTime.setText(timeFormat.format(mMediaPlayer.getCurrentPosition()));
                mRemoteViews.setTextViewText(R.id.tvCurrentTime, timeFormat.format(mMediaPlayer.getCurrentPosition()));
                //mNotificationManager.notify(0, mBuilder.build());
                mSeekBarPlay.setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(mp -> {
                    mPosition++;
                    if (mPosition > mListSong.size() - 1) {
                        mPosition = 0;
                    }
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        initMediaPlayer();
                        mMediaPlayer.start();
                        initRemoteViews();
                        mNotificationManager.notify(0, mBuilder.build());
                    } else {
                        initMediaPlayer();
                    }
                    setTotalTime();
                    updateCurrentTime();
                });
                //handler.postDelayed(this, 100);
            }
        };
       // handler.postDelayed(runnable, 100);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mRotateAnimation.setDuration(0);
                } else {
                    mMediaPlayer.start();
                    mImgDisk.startAnimation(mRotateAnimation);
                    mTvState.setText(R.string.play);
                    initRemoteViews();
                    mNotificationManager.notify(0, mBuilder.build());
                }
                setIcon();
                updateCurrentTime();
                setTotalTime();
                break;
            case R.id.imgBtnNext:
                mPosition++;
                if (mPosition > mListSong.size() - 1) {
                    mPosition = 0;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    initMediaPlayer();
                    mMediaPlayer.start();
                    initRemoteViews();
                    mNotificationManager.notify(0, mBuilder.build());
                } else {
                    initMediaPlayer();
                }
                setTotalTime();
                // updateCurrentTime();
                break;
            case R.id.imgBtnPrev:
                mPosition--;
                if (mPosition < 0) {
                    mPosition = mListSong.size() - 1;
                }
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    initMediaPlayer();
                    mMediaPlayer.start();
                    initRemoteViews();
                    mNotificationManager.notify(0, mBuilder.build());
                } else {
                    initMediaPlayer();
                }
                setTotalTime();
                // updateCurrentTime();
                break;
        }
    }
}
