package asiantech.internship.summer.service_and_broadcast_receiver.model;

public class Song {
    private String mTitle;
    private int mFile;
    private int mAvatarSinger;

    public Song(String mTitle, int mFile, int mAvatarSinger) {
        this.mTitle = mTitle;
        this.mFile = mFile;
        this.mAvatarSinger = mAvatarSinger;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmFile() {
        return mFile;
    }

    public int getmAvatarSinger() {
        return mAvatarSinger;
    }
}
