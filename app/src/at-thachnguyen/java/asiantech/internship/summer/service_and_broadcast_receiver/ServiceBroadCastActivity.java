package asiantech.internship.summer.service_and_broadcast_receiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.service_and_broadcast_receiver.model.Song;

public class ServiceBroadCastActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewSong;
    public static final String KEY_POSITION = "position";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_broad_cast);
        List<Song> mListSong = Song.getListSong();
        initView();
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_song));
        SongAdapter mSongAdapter = new SongAdapter(mListSong, this, position -> {
            Intent intent = new Intent(ServiceBroadCastActivity.this, ListenMusicActivity.class);
            intent.putExtra(KEY_POSITION, position);
            startActivity(intent);
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerViewSong.setAdapter(mSongAdapter);
        mRecyclerViewSong.setLayoutManager(layoutManager);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarListSong);
        mRecyclerViewSong = findViewById(R.id.recyclerViewSong);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
