package asiantech.internship.summer.exercise_recycler_view.model;

import java.io.Serializable;

public class TimelineItem implements Serializable {
    private int mImageAvatar;
    private String mUsername;
    private int mImagePost;
    private boolean mStateHeart;
    private String mDescriptionFood;

    public TimelineItem(int mImageAvatar, String mUsername, int mImagePost, boolean mStateHeart, String mDescriptionFood) {
        this.mImageAvatar = mImageAvatar;
        this.mUsername = mUsername;
        this.mImagePost = mImagePost;
        this.mStateHeart = mStateHeart;
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

    public boolean isStateHeart() {
        return mStateHeart;
    }

    public void setStateHeart(boolean mStateHeart) {
        this.mStateHeart = mStateHeart;
    }
}
