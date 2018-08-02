package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service_and_broadcast_receiver.model.Song;

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
    private int mPosition = 0;

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

    private void addListSong() {
        mListSong = new ArrayList<>();
        mListSong.add(new Song("Cuộc sống em ổn không", R.raw.cuoc_song_em_on_khong));
        mListSong.add(new Song("Đừng như thói quen", R.raw.dung_nhu_thoi_quen));
        mListSong.add(new Song("Đừng quên tên anh", R.raw.dung_quen_ten_anh));
        mListSong.add(new Song("Lỡ thương một người", R.raw.lo_thuong_mot_nguoi));
        mListSong.add(new Song("Rồi người thương cũng hóa người dưng", R.raw.roi_nguoi_thuong_cung_hoa_nguoi_dung));
        mListSong.add(new Song("Sai người sai thời điểm", R.raw.sai_nguoi_sai_thoi_diem));
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
                mSeekBarPlay.setProgress(mMediaPlayer.getCurrentPosition());
                mTvCurrentTime.postDelayed(this, 500);
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
                    } else {
                        initMediaPlayer();
                    }
                    setTotalTime();
                    updateCurrentTime();
                });
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mRotateAnimation.setDuration(0);
                    mTvState.setText(R.string.pause);
                    mImgBtnPlay.setImageResource(R.drawable.ic_play);
                } else {
                    mMediaPlayer.start();
                    mImgDisk.startAnimation(mRotateAnimation);
                    mTvState.setText(R.string.play);
                    mImgBtnPlay.setImageResource(R.drawable.ic_pause);
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
                    initMediaPlayer();
                    mMediaPlayer.start();
                } else {
                    initMediaPlayer();
                }
                setTotalTime();
                updateCurrentTime();
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
                } else {
                    initMediaPlayer();
                }
                setTotalTime();
                updateCurrentTime();
                break;
        }
    }
}
