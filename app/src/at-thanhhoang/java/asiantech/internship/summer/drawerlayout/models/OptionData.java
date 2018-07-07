package asiantech.internship.summer.drawerlayout.models;

public class OptionData {
    private int mOptionImage;
    private String mOptionName;

    public OptionData(int mOptionImage, String mOptionName) {
        this.mOptionImage = mOptionImage;
        this.mOptionName = mOptionName;
    }

    public int getOptionImage() {
        return mOptionImage;
    }

    public void setOptionImage(int mOptionImage) {
        this.mOptionImage = mOptionImage;
    }

    public String getOptionName() {
        return mOptionName;
    }

    public void setOptionName(String mOptionName) {
        this.mOptionName = mOptionName;
    }
}
