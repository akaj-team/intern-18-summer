package asiantech.internship.summer.thachnguyen.debug.recyclerview.model;

import java.io.Serializable;

public class TimelineItem implements Serializable{
    private final Owner mOwner;
    private final int mImage;
    private final String mDescription;
    private int mLike;
    private boolean mCheckLike;

    public TimelineItem(Owner mOwner, int mImage, String mDescription, int mLike, boolean mCheckLike) {
        this.mOwner = mOwner;
        this.mImage = mImage;
        this.mDescription = mDescription;
        this.mLike = mLike;
        this.mCheckLike = mCheckLike;
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

    public boolean ismCheckLike() {
        return mCheckLike;
    }

    public void setmLike(int mLike) {
        this.mLike = mLike;
    }

    public void setmCheckLike(boolean mCheckLike) {
        this.mCheckLike = mCheckLike;
    }
}
