package asiantech.internship.summer.thachnguyen.debug.recyclerview.model;

import java.util.ArrayList;
import java.util.Random;

import asiantech.internship.summer.thachnguyen.debug.recyclerview.RecyclerViewActivity;

public class TimelineItem {
    private Owner mOwner;
    private int mImage;
    private String mDescription;
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
    public static ArrayList<TimelineItem> creatListTimeline(){
        ArrayList<TimelineItem> listTimeLine=new ArrayList<>();
        for (int i = 0; i <23 ; i++) {
            Owner owner=new Owner(RecyclerViewActivity.getName(i%5), RecyclerViewActivity.getAvatar( i%5));
            listTimeLine.add(new TimelineItem(owner, RecyclerViewActivity.randomImageFood("food", 22), RecyclerViewActivity.getDescription(i), 0));
        }
        return listTimeLine;
    }
}

