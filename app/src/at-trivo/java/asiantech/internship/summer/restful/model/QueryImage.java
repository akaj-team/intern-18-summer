package asiantech.internship.summer.restful.model;

import com.google.gson.annotations.SerializedName;

public class QueryImage extends Image {
    @SerializedName("created_at")
    public String createdAt;
}
