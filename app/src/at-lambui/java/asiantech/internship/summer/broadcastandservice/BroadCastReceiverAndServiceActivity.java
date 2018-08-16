package asiantech.internship.summer.broadcastandservice;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.broadcastandservice.adapter.ListSongAdapter;
import asiantech.internship.summer.broadcastandservice.model.Song;

import static asiantech.internship.summer.broadcastandservice.MusicSimplePlayerService.ACTION_PAUSE;
import static asiantech.internship.summer.broadcastandservice.MusicSimplePlayerService.ACTION_UN_PAUSE;
import static asiantech.internship.summer.broadcastandservice.MusicSimplePlayerService.DURATION_ACTION;
import static asiantech.internship.summer.broadcastandservice.MusicSimplePlayerService.SONG_ACTION;
import static asiantech.internship.summer.broadcastandservice.MusicSimplePlayerService.UPDATE_TIMER_SONG;

public class BroadCastReceiverAndServiceActivity extends AppCompatActivity implements View.OnClickListener,
        OnplayerEventListener, OnClickListenerSong {
    private ImageView mImgCircleMain;
    private SeekBar mSeekBar;
    private TextView mTvStartRuntime;
    private TextView mTvStatus;
    private TextView mTvTotalRunTime;
    private ImageView mImgBackSong;
    private ImageView mImgPlay;
    private ImageView mImgNextSong;
    private RecyclerView mRecycleViewSong;
    private ListSongAdapter mListSongAdapter;
    private List<Song> mListSongs = new ArrayList<>();
    public static final int RUNTIME_PERMISSION_CODE = 7;
    private final int PERMISSION_CODE_STORAGE = 1;
    private DurationReceiver mDurationReceiver;
    private MusicSimplePlayerService mMusicSimplePlayerService;
    private boolean isRunning = true;
    private boolean isBound = false;
    public ServiceConnection myConnecttion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            MusicSimplePlayerService.LocalBinder binder = (MusicSimplePlayerService.LocalBinder) iBinder;
            mMusicSimplePlayerService = binder.getservice();
            mMusicSimplePlayerService.initSong(mListSongs);
            if (mMusicSimplePlayerService != null) {
                mMusicSimplePlayerService.updateDefault();
                if (!mMusicSimplePlayerService.updateStateDefault()) {
                    mTvStatus.setText(getResources().getString(R.string.message_music_pause));
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    if (mImgCircleMain.getAnimation() != null) {
                        mImgCircleMain.getAnimation().cancel();
                    }
                } else {
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    mTvStatus.setText(getResources().getString(R.string.message_music_running));
                }
            }
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_receiver_and_service);
        initViews();
        addListener();
        initRecyclerView();
        initMusic();
        Intent intentservice = new Intent(this, MusicSimplePlayerService.class);
        bindService(intentservice, myConnecttion, Context.BIND_AUTO_CREATE);
        intentservice.setAction(MusicSimplePlayerService.ACTION_SERVICE);
        startService(intentservice);
    }

    @Override
    protected void onDestroy() {
        if (isBound) {
            unbindService(myConnecttion);
        }
        super.onDestroy();
    }

    private void initViews() {
        mImgCircleMain = findViewById(R.id.imgCircleMain);
        mSeekBar = findViewById(R.id.seekBar);
        mImgBackSong = findViewById(R.id.imgBackSong);
        mImgPlay = findViewById(R.id.imgPlay);
        mImgNextSong = findViewById(R.id.imgNextSong);
        mTvStartRuntime = findViewById(R.id.tvStartRuntime);
        mTvStatus = findViewById(R.id.tvStatus);
        mTvTotalRunTime = findViewById(R.id.tvTotalRunTime);
        mRecycleViewSong = findViewById(R.id.recycleViewSong);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMusicSimplePlayerService.setProgessSeekbar(seekBar);
            }
        });
    }

    private void addListener() {
        mImgBackSong.setOnClickListener(this);
        mImgPlay.setOnClickListener(this);
        mImgNextSong.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecycleViewSong.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleViewSong.setLayoutManager(linearLayoutManager);
        mListSongAdapter = new ListSongAdapter(mListSongs, this, this);
        mRecycleViewSong.setAdapter(mListSongAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecycleViewSong.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(this), R.drawable.custom_item);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(drawable));
        mRecycleViewSong.addItemDecoration(dividerItemDecoration);
    }

    private void initMusic() {
        if ((ContextCompat.checkSelfPermission(BroadCastReceiverAndServiceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(BroadCastReceiverAndServiceActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
        getAllSongs();
    }

    public void getAllSongs() {
        ContentResolver contentResolver = this.getContentResolver();
        Uri uri;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor;
        cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "Query fail", Toast.LENGTH_LONG).show();
        } else if (!cursor.moveToFirst()) {
            Toast.makeText(this, "No music on SDCard", Toast.LENGTH_LONG).show();
        } else {
            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationcolumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                long songId = cursor.getLong(id);
                String nameSong = cursor.getString(Title);
                String nameArtist = cursor.getString(artist);
                String filepath = cursor.getString(pathColumn);
                long duration = cursor.getLong(durationcolumn);
                Song song = new Song(songId, nameSong, nameArtist, filepath, duration);
                mListSongs.add(song);
            } while (cursor.moveToNext());
            cursor.close();
        }
        mListSongAdapter.notifyDataSetChanged();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_dialog))
                    .setMessage(getResources().getString(R.string.message_dialog))
                    .setPositiveButton(getResources().getString(R.string.ok), (dialog, i) -> ActivityCompat.requestPermissions(BroadCastReceiverAndServiceActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE))
                    .setNegativeButton(getResources().getString(R.string.cancel), (dialog, i) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.message_failure), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPauseSong() {
        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        mTvStatus.setText(getResources().getString(R.string.message_music_pause));
    }

    @Override
    public void onUnPauseSong() {
    }

    @Override
    public void onPlayerPlaying(long time) {
    }

    @Override
    public void onPlayerStart(String title, int duration) {
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(0);
        mTvTotalRunTime.setText(GetDurationTransfer.getDuration(duration));
    }

    @Override
    public void onSongClicked(Song song) {
        mMusicSimplePlayerService.chooseSong(song);
        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        mTvStatus.setText(getResources().getString(R.string.message_music_running));
        isRunning = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackSong:
                mMusicSimplePlayerService.backSong();
                mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                break;
            case R.id.imgNextSong:
                mMusicSimplePlayerService.nextSong();
                mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                break;
            case R.id.imgPlay:
                if (!isRunning) {
                    mTvStatus.setText(getResources().getString(R.string.message_music_pause));
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    mMusicSimplePlayerService.pauseSong();
                    isRunning = true;
                } else {
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    mTvStatus.setText(getResources().getString(R.string.message_music_running));
                    mMusicSimplePlayerService.playSong();
                    isRunning = false;
                }
                rotateCircle();
                break;
        }
    }

    public class DurationReceiver extends BroadcastReceiver {

        public DurationReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String value = intent.getAction();
            Log.e("BBB", value);
            if (value != null) {
                switch (value) {
                    case SONG_ACTION:
//                        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                        String nameIntent = intent.getStringExtra(MusicSimplePlayerService.TITLE_SONG);
                        long duration = intent.getIntExtra(MusicSimplePlayerService.DURATION_SONG, 0);
                        onPlayerStart(nameIntent, (int) duration);
                        break;

                    case UPDATE_TIMER_SONG:
                        long time = intent.getLongExtra(MusicSimplePlayerService.CURRENT_TIME, 0);
                        updateTimer((int) time);
                        break;
                    case ACTION_PAUSE:
                        Log.e("sss", "onReceive: ccc" + intent.getAction());
                        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                        mTvStatus.setText(getResources().getString(R.string.message_music_pause));
                        Log.e("sss", "onReceive: ccc" + mImgCircleMain.getAnimation());
                        if (mImgCircleMain.getAnimation() != null) {
                            mImgCircleMain.getAnimation().cancel();
                        }
                        break;
                    case ACTION_UN_PAUSE:
                        Log.e("sss", "onReceive: ccc" + intent.getAction());
                        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                        mTvStatus.setText(getResources().getString(R.string.message_music_running));
                        rotateCircle();
                }
            }
        }
    }

    protected void onResume() {

        super.onResume();
        // null cho này.
        if (mDurationReceiver == null) {
            mDurationReceiver = new DurationReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(DURATION_ACTION);
        filter.addAction(SONG_ACTION);
        filter.addAction(UPDATE_TIMER_SONG);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_UN_PAUSE);
        registerReceiver(mDurationReceiver, filter);

    }

    protected void onPause() {
        super.onPause();
        if (mDurationReceiver != null) {
            unregisterReceiver(mDurationReceiver);
        }
    }

    private void updateTimer(int time) {
        mSeekBar.setProgress(time);
        mTvStartRuntime.setText(GetDurationTransfer.getDuration(time));
    }

    private void rotateCircle() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        mImgCircleMain.setAnimation(rotateAnimation);
    }
}
