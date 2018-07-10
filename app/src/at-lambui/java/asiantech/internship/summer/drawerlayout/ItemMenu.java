package asiantech.internship.summer.drawerlayout;

public class ItemMenu {
    private String mTitleItem;
    private int mImageItem;

    ItemMenu(String TitleItem, int ImageItem) {
        this.mTitleItem = TitleItem;
        this.mImageItem = ImageItem;
    }

    public String getTitleItem() {
        return mTitleItem;
    }

    public int getImageItem() {
        return mImageItem;
    }
}
