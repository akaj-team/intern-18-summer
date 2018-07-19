package asiantech.internship.summer.rest_api.remote;

public class ApiUtils {

    public static Api getServiceDownload() {
        return RetrofitDownloadClient.getClient().create(Api.class);
    }

    public static Api getServiceUpload() {
        return RetrofitUploadClient.getClient().create(Api.class);
    }
}
