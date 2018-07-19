package asiantech.internship.summer.rest_api.remote;

import java.util.List;

import asiantech.internship.summer.rest_api.models.ImageInfo;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    @GET("images")
    Call<List<ImageInfo>> downloadImages(@Query("access_token") String accessToken);

    @Multipart
    @POST("upload")
    Call<Void> uploadImages(@Query("access_token") String accessToken, @Part MultipartBody.Part image);
}
