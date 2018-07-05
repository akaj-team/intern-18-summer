package asiantech.internship.summer.drawer_layout.model;

public class MenuItem {
    private String mTitle;
    private int mImage;

    public MenuItem(String mTitle, int mImage) {
        this.mTitle = mTitle;
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmImage() {
        return mImage;
    }
}