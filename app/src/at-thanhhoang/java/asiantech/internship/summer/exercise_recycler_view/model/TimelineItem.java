package asiantech.internship.summer.exercise_recycler_view.model;

import java.io.Serializable;
import java.util.ArrayList;

import asiantech.internship.summer.exercise_recycler_view.TimelineFragment;

public class TimelineItem implements Serializable {

    private int mImageAvatar;
    private String mUsername;
    private int mImagePost;
    private String mDescriptionFood;

    private TimelineItem(int mImageAvatar, String mUsername, int mImagePost, String mDescriptionFood) {
        this.mImageAvatar = mImageAvatar;
        this.mUsername = mUsername;
        this.mImagePost = mImagePost;
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

    public static ArrayList<TimelineItem> createTimeLineList() {
        ArrayList<TimelineItem> timelineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int idImageAvatar = TimelineFragment.getRandomImageAvatar();
            String username = TimelineFragment.getRandomUsername();
            int idImageFood = TimelineFragment.getRandomImageFood();
            String description = TimelineFragment.getRandomDescription();

            String des = "<font color='black'>" + username + "</font>";

            timelineList.add(new TimelineItem(idImageAvatar, username, idImageFood, des + " " + description));
        }
        return timelineList;
    }
}
