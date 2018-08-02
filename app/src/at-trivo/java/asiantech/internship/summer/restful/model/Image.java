package asiantech.internship.summer.restful.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("image_id")
    public String imageId;
    @SerializedName("permalink_url")
    public String permalinkUrl;
    @SerializedName("thumb_url")
    public String thumbUrl;
    public String url;
    public String type;
}
