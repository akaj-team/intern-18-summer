package asiantech.internship.summer.service_and_broadcast_receiver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class ServiceBroadCastActivity extends AppCompatActivity {
    private ImageView mImgDisk;
    private SeekBar mSeekBarPlay;
    private TextView mTvCurrentTime;
    private TextView mTvState;
    private TextView mTvTotalTime;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnNext;
    private RotateAnimation mRotateAnimation;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_broad_cast);
        initView();
        mMediaPlayer=MediaPlayer.create(ServiceBroadCastActivity.this, R.raw.cuoc_song_em_on_khong);
        mImgBtnPlay.setOnClickListener(v -> {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
                stopRotateDisk();
                mTvState.setText(R.string.pause);
                mImgBtnPlay.setImageResource(R.drawable.ic_play);
            }
            else {
                mMediaPlayer.start();
                startRotateDisk();
                mTvState.setText(R.string.play);
                mImgBtnPlay.setImageResource(R.drawable.ic_pause);
            }

        });
    }

    private void initView(){
        mImgDisk=findViewById(R.id.imgDisk);
        mTvCurrentTime=findViewById(R.id.tvCurrentTime);
        mTvState=findViewById(R.id.tvState);
        mTvTotalTime=findViewById(R.id.tvTotalTime);
        mImgBtnPrev=findViewById(R.id.imgBtnPrev);
        mImgBtnPlay=findViewById(R.id.imgBtnPlay);
        mImgBtnNext=findViewById(R.id.imgBtnNext);
    }

    private void startRotateDisk(){
        mRotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(2000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mImgDisk.startAnimation(mRotateAnimation);
    }

    private void stopRotateDisk(){
        mRotateAnimation.setRepeatCount(0);
    }
}
