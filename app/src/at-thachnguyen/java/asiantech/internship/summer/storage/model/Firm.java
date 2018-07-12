package asiantech.internship.summer.storage.model;

public class Firm {
    private final int mId;
    private final String mName;
    public static final String TABLE_NAME_FIRM = "firms";
    private static final String COL_ID="id";
    public static final String COL_NAME="name";
    public static final String CREATE_TABLE_FIRM =
            "CREATE TABLE " + TABLE_NAME_FIRM + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NAME + " TEXT)";

    public Firm(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }
}
