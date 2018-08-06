package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("Registered, StaticFieldLeak")
public class ServiceBroadCastActivity extends AppCompatActivity {
    public static CircleImageView sImgDisk;
    public static SeekBar sSeekBarPlay;
    public static TextView sTvCurrentTime;
    public static TextView sTvState;
    public static TextView sTvTotalTime;
    public static TextView sTvTitleSong;
    public static ImageButton sImgBtnPrev;
    public static ImageButton sImgBtnPlay;
    public static ImageButton sImgBtnNext;
    private Intent mPlayIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_broad_cast);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(this, MusicService.class);
            startService(mPlayIntent);
        }
    }

    private void initView() {
        sImgDisk = findViewById(R.id.imgDisk);
        sTvCurrentTime = findViewById(R.id.tvCurrentTime);
        sTvState = findViewById(R.id.tvState);
        sTvTotalTime = findViewById(R.id.tvTotalTime);
        sTvTitleSong = findViewById(R.id.tvTitleSong);
        sImgBtnPrev = findViewById(R.id.imgBtnPrev);
        sImgBtnPlay = findViewById(R.id.imgBtnPlay);
        sImgBtnNext = findViewById(R.id.imgBtnNext);
        sSeekBarPlay = findViewById(R.id.seekBarPlay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mPlayIntent);
    }
}
