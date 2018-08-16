package asiantech.internship.summer.canvas.models;

public class Wildlife {
    private int year;
    private int dolphins;
    private int whales;

    public Wildlife(int year, int dolphins, int whales) {
        this.year = year;
        this.dolphins = dolphins;
        this.whales = whales;
    }

    public int getYear() {
        return year;
    }

    public int getDolphins() {
        return dolphins;
    }

    public int getWhales() {
        return whales;
    }
}
