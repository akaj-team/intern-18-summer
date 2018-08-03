package asiantech.internship.summer.timeline.model;

import java.io.Serializable;

public class TimelineItem implements Serializable {
    private int mImageAvatar;
    private String mUsername;
    private int mImagePost;
    private boolean mStateLikes;
    private String mDescriptionFood;

    public TimelineItem(int mImageAvatar, String mUsername, int mImagePost, boolean mStateLikes, String mDescriptionFood) {
        this.mImageAvatar = mImageAvatar;
        this.mUsername = mUsername;
        this.mImagePost = mImagePost;
        this.mStateLikes = mStateLikes;
        this.mDescriptionFood = mDescriptionFood;
    }

    public int getImageAvatar() {
        return mImageAvatar;
    }

    public void setImageAvatar(int mIconAvatar) {
        this.mImageAvatar = mIconAvatar;
    }

    public String getUsername() {
        return mUsername;
    }

    public int getImagePost() {
        return mImagePost;
    }

    public String getDescriptionFood() {
        return mDescriptionFood;
    }

    public boolean isStateLikes() {
        return mStateLikes;
    }

    public void setStateLikes(boolean mStateLikes) {
        this.mStateLikes = mStateLikes;
    }
}
