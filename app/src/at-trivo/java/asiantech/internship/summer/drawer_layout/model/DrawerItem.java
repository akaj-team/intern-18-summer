package asiantech.internship.summer.drawer_layout.model;

public class DrawerItem {

    private int imageId;
    private int titleId;
    private boolean isSelected;

    public DrawerItem(int imageId, int titleId) {
        this.imageId = imageId;
        this.titleId = titleId;
    }

    public int getImageId() {
        return imageId;
    }

    public int getTitleId() {
        return titleId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean mIsSelected) {
        this.isSelected = mIsSelected;
    }
}