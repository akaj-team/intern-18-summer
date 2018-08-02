package asiantech.internship.summer.restful.helpers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public final class Uri2PathHelper {

    private Uri2PathHelper() {
        // No-op
    }

    /**
     * @param contentResolver content resolver get from context
     * @param contentUri      image's uri from onActivityResult
     * @return real path of image
     */
    public static String getRealPathFromURI(ContentResolver contentResolver, Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);
        String result = null;
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }
}
