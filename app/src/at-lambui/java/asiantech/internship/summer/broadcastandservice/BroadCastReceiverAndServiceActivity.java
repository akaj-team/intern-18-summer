package asiantech.internship.summer.broadcastandservice;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
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

public class BroadCastReceiverAndServiceActivity extends AppCompatActivity implements View.OnClickListener, OnplayerEventListener,OnClickListenerSong {
    private ImageView mImgCircleMain;
    private SeekBar mSeekBar;
    private TextView mTvStartRuntime;
    private TextView mTvStatus;
    private TextView mTvTotalRunTime;
    private ImageView mImgBackSong;
    private ImageView mImgPlay;
    private ImageView mImgNextSong;
    private RecyclerView mRecycleViewSong;
    private LinearLayoutManager mLinearLayoutManager;
    private ListSongAdapter mListSongAdapter;
    private List<Song> mListSongs = new ArrayList<>();
    public static final int RUNTIME_PERMISSION_CODE = 7;
    private ContentResolver mContentResolver;
    private final int PERMISSION_CODE_STORAGE = 1;
    private simplePlayer mSimplePlayer;
    public static final String DURATION_KEY = "Duration key";
    private boolean isRunning = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_receiver_and_service);
        initViews();
        addListener();
        initRecyclerView();
        initMusic();
        if (mSimplePlayer == null){
            mSimplePlayer = new simplePlayer(this);
            mSimplePlayer.init(mListSongs);
            mSimplePlayer.play();
            mSeekBar.setMax((int) (mSimplePlayer.mCurrentDuration));
        }
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
    }
    private void addListener(){
        mImgBackSong.setOnClickListener(this);
        mImgPlay.setOnClickListener(this);
        mImgNextSong.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecycleViewSong.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleViewSong.setLayoutManager(mLinearLayoutManager);
        mListSongAdapter = new ListSongAdapter(mListSongs,this, this);
        mRecycleViewSong.setAdapter(mListSongAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecycleViewSong.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(this), R.drawable.custom_item);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(drawable));
        mRecycleViewSong.addItemDecoration(dividerItemDecoration);
    }

    private void initMusic(){
        if ((ContextCompat.checkSelfPermission(BroadCastReceiverAndServiceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(BroadCastReceiverAndServiceActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();
            // Permission is not granted
        } else {
            requestStoragePermission();
        }
        getAllSongs();
    }
    public void getAllSongs() {
        mContentResolver = this.getContentResolver();
        Uri uri;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor;
        cursor = mContentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "Trat lat roi", Toast.LENGTH_LONG).show();
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
                Song song = new Song(songId, nameSong, nameArtist,filepath,duration);
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
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(this, getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.message_failure), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPlayerCompleted() {

    }

    @Override
    public void onPlayerStart(String title, int duration) {
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(0);
        mTvTotalRunTime.setText(getResources().getString(duration));
    }

    @Override
    public void onSongClicked(Song song) {
        Log.e("xxx", "" + song);
        mSimplePlayer.chooseSong(song);
        mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        mTvStatus.setText(getResources().getString(R.string.message_music_running));
        isRunning = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBackSong:
                mSimplePlayer.previousSong();
                break;
            case R.id.imgNextSong:
                mSimplePlayer.nextSong();
                break;
            case R.id.imgPlay:
                if (!isRunning){
                    mTvStatus.setText(getResources().getString(R.string.message_music_pause));
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    mSimplePlayer.pause();
                   isRunning = true;
                }else {
                    mImgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    mTvStatus.setText(getResources().getString(R.string.message_music_running));
                    mSimplePlayer.play();
                    isRunning = false;
                }
                break;
        }
    }
    private class durationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                mSeekBar.setProgress(bundle.getInt(DURATION_KEY));
                mTvStartRuntime.setText();
            }


        }
    }
}
