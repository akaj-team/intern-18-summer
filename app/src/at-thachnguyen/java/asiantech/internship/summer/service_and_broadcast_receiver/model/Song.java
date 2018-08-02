package asiantech.internship.summer.service_and_broadcast_receiver.model;

public class Song {
    private String mTitle;
    private int mFile;

    public Song(String mTitle, int mFile) {
        this.mTitle = mTitle;
        this.mFile = mFile;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmFile() {
        return mFile;
    }

    public void setmFile(int mFile) {
        this.mFile = mFile;
    }
}
