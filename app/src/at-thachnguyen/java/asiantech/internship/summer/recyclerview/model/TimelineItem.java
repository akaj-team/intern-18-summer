package asiantech.internship.summer.recyclerview.model;

public class TimelineItem {
    private final Owner mOwner;
    private final int mImage;
    private final String mDescription;
    private int mLike;
    private boolean mIsLike;

    public TimelineItem(Owner mOwner, int mImage, String mDescription, int mLike, boolean mIsLike) {
        this.mOwner = mOwner;
        this.mImage = mImage;
        this.mDescription = mDescription;
        this.mLike = mLike;
        this.mIsLike = mIsLike;
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

    public boolean ismIsLike() {
        return mIsLike;
    }

    public void setmIsLike(boolean mIsLike) {
        this.mIsLike = mIsLike;
    }
}
