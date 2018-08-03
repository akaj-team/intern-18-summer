package asiantech.internship.summer.recycler_view.model;

public class Timeline {
    private Author mAuthor;
    private String mDescription;
    private int mTimelineImageId;
    private int mLoveNumber;
    private boolean mIsLoved;

    Timeline(Author author, String description, int timeLineImageId) {
        mAuthor = author;
        mDescription = description;
        mTimelineImageId = timeLineImageId;
        mLoveNumber = 0;
        mIsLoved = false;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getTimelineImageId() {
        return mTimelineImageId;
    }

    public int getLoveNumber() {
        return mLoveNumber;
    }

    public boolean isLoved() {
        return mIsLoved;
    }

    public void changeLoveState() {
        if (mIsLoved) {
            this.mLoveNumber--;
        } else {
            this.mLoveNumber++;
        }
        mIsLoved = !mIsLoved;
    }
}
