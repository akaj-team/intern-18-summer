package asiantech.internship.summer.retrofitandgson.api;

import java.util.List;

import asiantech.internship.summer.retrofitandgson.model.CustomImage;
import asiantech.internship.summer.retrofitandgson.model.Image;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ImageAPI {
     String ACCESS_TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    /**
     * @param access_token token of user that API supplied
     * @param per_page     page of image,default 1
     * @return list image
     */
    @GET("images")
    Call<List<Image>> getData(@Query("access_token") String access_token,
                              @Query("per_page") int per_page);

    /**
     * @param accesstoken token of user that API supplied
     * @param image       image uploaded
     * @return image
     */
    @Multipart
    @POST("upload")
    Call<CustomImage> postData(@Part("access_token") RequestBody accesstoken,
                               @Part MultipartBody.Part image);
}
