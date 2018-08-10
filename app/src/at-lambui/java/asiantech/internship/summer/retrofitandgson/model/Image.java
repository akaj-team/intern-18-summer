package asiantech.internship.summer.retrofitandgson.model;

public class Image {
    private String url;
    private String type;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
