package asiantech.internship.summer.broadcastandservice;

public interface OnplayerEventListener {
    void onPauseSong();

    void onUnPauseSong();

    void onPlayerPlaying(long time);//update

    void onPlayerStart(String title, int duration);
}
