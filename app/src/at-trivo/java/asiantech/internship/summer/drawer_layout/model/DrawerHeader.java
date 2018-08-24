package asiantech.internship.summer.drawer_layout.model;

import android.net.Uri;

public class DrawerHeader {

    private int avatarId;
    private int emailArrayId;
    private Uri uri;

    public DrawerHeader(int avatarId, int arrayEmailId) {
        this.avatarId = avatarId;
        this.emailArrayId = arrayEmailId;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri mUri) {
        this.uri = mUri;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public int getEmailArrayId() {
        return emailArrayId;
    }
}
