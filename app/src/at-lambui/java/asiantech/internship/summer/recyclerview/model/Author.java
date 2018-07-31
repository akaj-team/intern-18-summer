package asiantech.internship.summer.recyclerview.model;

public class Author {
    private int mAvatar;
    private String mName;

    Author(int Avatar, String Name) {
        this.mAvatar = Avatar;
        this.mName = Name;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }
}
