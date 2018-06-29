package asiantech.internship.summer.thachnguyen.debug.recyclerview.model;

public class TimelineItem {
    private final Owner mOwner;
    private final int mImage;
    private final String mDescription;
    private int mLike;

    public TimelineItem(Owner mOwner, int mImage, String mDescription, int mLike) {
        this.mOwner = mOwner;
        this.mImage = mImage;
        this.mDescription = mDescription;
        this.mLike = mLike;
    }

    public Owner getmOwner() {
        return mOwner;
    }

    public int getmImage() {
        return mImage;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getmLike() {
        return mLike;
    }

    public void setmLike(int mLike) {
        this.mLike = mLike;
    }
}

