package asiantech.internship.summer.drawerlayout;

import android.net.Uri;
public class UserHeader {
    private int mAvatar;
    private Uri mUri;

    UserHeader(int Avatar) {
        this.mAvatar = Avatar;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

}
