package asiantech.internship.summer.restful;

import java.util.List;

import asiantech.internship.summer.restful.model.Photo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * ImageAPI.java
 * Interface class that has the following methods.
 * listPhotos: to get images from API
 * uploadFile: to upload image from client into API
 * <p>
 * <p>
 * Create by Thach Nguyen H.
 *
 * @since 07-18-2018
 */
public interface ImageAPI {
    int PER_PAGE = 50;

    /**
     * this function is used to get list images from api
     *
     * @param token   is access_token of api
     * @param perPage is amount item which is loaded per page
     * @return list images
     */
    @GET("images")
    Call<List<Photo>> listPhotos(@Query("access_token") String token,
                                 @Query("per_page") int perPage);

    /**
     * this function is used to upload image into api
     *
     * @param token is access_token of api
     * @param file  is file, which is uploaded into api from device
     * @return image
     */
    @Multipart
    @POST("upload")
    Call<Photo> uploadFile(@Part("access_token") RequestBody token, @Part MultipartBody.Part file);
}
