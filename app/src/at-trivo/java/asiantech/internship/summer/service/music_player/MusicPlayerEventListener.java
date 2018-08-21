package asiantech.internship.summer.service.music_player;

public interface MusicPlayerEventListener {
    void onPlayerStart(String title, int duration);
    void onPlayerPlaying(int time);
    void onPlayerPause();
    void onPlayerUnPause();
}
