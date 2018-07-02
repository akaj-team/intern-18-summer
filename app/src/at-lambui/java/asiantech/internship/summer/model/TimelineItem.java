package asiantech.internship.summer.model;
import java.util.ArrayList;
import java.util.Random;
import asiantech.internship.summer.R;
public class TimelineItem {
    private int mImage;
    private String mDescription;
    private Author mAuthor;

    private TimelineItem(int Image, String Description, Author Author) {
        this.mImage = Image;
        this.mDescription = Description;
        this.mAuthor = Author;
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

    public static ArrayList<TimelineItem> createListItem() {
        ArrayList<TimelineItem> listItem = new ArrayList<>();
        ArrayList<Integer> listImage = new ArrayList<>();
        ArrayList<String> listDescription = new ArrayList<>();
        ArrayList<Author> listAuthor = new ArrayList<>();
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
}
