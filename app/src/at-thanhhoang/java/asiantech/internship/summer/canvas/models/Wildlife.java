package asiantech.internship.summer.canvas.models;

public class Wildlife {
    private int mYear;
    private int mDolphins;
    private int mWhales;

    public Wildlife(int mYear, int mDolphins, int mWhales) {
        this.mYear = mYear;
        this.mDolphins = mDolphins;
        this.mWhales = mWhales;
    }

    public int getYear() {
        return mYear;
    }

    public int getDolphins() {
        return mDolphins;
    }

    public int getWhales() {
        return mWhales;
    }
}
