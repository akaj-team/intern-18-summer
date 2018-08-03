package asiantech.internship.summer.recyclerview.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;

public class TimelineItem {
    private int mImage;
    private String mDescription;
    private Author mAuthor;
    private int mNumberLike;
    private boolean mIsLiked;

    private TimelineItem(int image, String description, Author author) {
        this.mImage = image;
        this.mDescription = description;
        this.mAuthor = author;
        this.mNumberLike = 0;
        this.mIsLiked = false;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int Image) {
        this.mImage = Image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String Description) {
        this.mDescription = Description;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public int getmNumberLike() {
        return mNumberLike;
    }

    public boolean ismIsLiked() {
        return mIsLiked;
    }

    public static List<TimelineItem> createListItem() {
        List<TimelineItem> listItem = new ArrayList<>();
        List<Integer> listImage = new ArrayList<>();
        List<String> listDescription = new ArrayList<>();
        List<Author> listAuthor = new ArrayList<>();
        listImage.add(R.drawable.mon_an_tonlatsu);
        listImage.add(R.drawable.tom_chien);
        listImage.add(R.drawable.ga_nuong);
        listImage.add(R.drawable.tomcuon);
        listImage.add(R.drawable.tomnuong);
        listImage.add(R.drawable.dau_phu);
        listDescription.add("ngon bo re");
        listDescription.add("phong cach nguoi viet");
        listDescription.add("chat nhu qua gat");
        listDescription.add("mon ngon bo, dam chat mien que");
        listDescription.add("dam chat mien que ");
        listDescription.add("very special");
        listAuthor.add(new Author(R.drawable.img_avatar_a, "ronaldo"));
        listAuthor.add(new Author(R.drawable.img_avatar_c, "messi"));
        listAuthor.add(new Author(R.drawable.img_avatar_d, "Bale"));
        listAuthor.add(new Author(R.drawable.img_avatar_e, "calor"));
        listAuthor.add(new Author(R.drawable.img_avatar_g, "duc lam"));
        listAuthor.add(new Author(R.drawable.img_avatar_l, "suarez"));
        for (int i = 1; i <= 10; i++) {
            Random random = new Random();
            int position = random.nextInt(6);
            TimelineItem timelineItem = new TimelineItem(listImage.get(position), listDescription.get(position), listAuthor.get(position));
            listItem.add(timelineItem);
        }
        return listItem;
    }

    public void changenumberlike() {
        if (mIsLiked) {
            mNumberLike--;
        } else {
            mNumberLike++;
        }
        mIsLiked = !mIsLiked;
    }
}
