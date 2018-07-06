package asiantech.internship.summer.drawerlayout.models;

public class Data {
    private int mImage;
    private String mText;

    public Data(int mImage, String mText) {
        this.mImage = mImage;
        this.mText = mText;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }
}
