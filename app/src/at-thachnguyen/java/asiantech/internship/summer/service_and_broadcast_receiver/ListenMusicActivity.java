package asiantech.internship.summer.service_and_broadcast_receiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("Registered, StaticFieldLeak")
public class ListenMusicActivity extends AppCompatActivity {
    public static CircleImageView sImgDisk;
    public static SeekBar sSeekBarPlay;
    public static TextView sTvCurrentTime;
    public static TextView sTvState;
    public static TextView sTvTotalTime;
    public static TextView sTvTitleSong;
    public static ImageButton sImgBtnPrev;
    public static ImageButton sImgBtnPlay;
    public static ImageButton sImgBtnNext;
    private Toolbar mToolbarListenMusic;
    private Intent mPlayIntent;
    private int mPosition;
    public static final String KEY_POSITION = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_music);
        initView();
        setSupportActionBar(mToolbarListenMusic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.listen_music));
        Intent intent = getIntent();
        mPosition = intent.getIntExtra(ServiceBroadCastActivity.KEY_POSITION, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(this, MusicService.class);
            mPlayIntent.setAction("move");
            mPlayIntent.putExtra(KEY_POSITION, mPosition);
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
        mToolbarListenMusic = findViewById(R.id.toolbarListenMusic);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
