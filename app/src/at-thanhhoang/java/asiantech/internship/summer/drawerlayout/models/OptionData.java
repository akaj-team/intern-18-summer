package asiantech.internship.summer.drawerlayout.models;

import android.graphics.drawable.Drawable;

public class OptionData {
    private Drawable mOptionImage;
    private String mOptionName;

    public OptionData(Drawable mOptionImage, String mOptionName) {
        this.mOptionImage = mOptionImage;
        this.mOptionName = mOptionName;
    }

    public Drawable getOptionImage() {
        return mOptionImage;
    }

    public String getOptionName() {
        return mOptionName;
    }
}
