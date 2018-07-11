package asiantech.internship.summer.storage.model;

public class Employee {
    private int mId;
    private String mName;
    public static final String TABLE_NAME_EMPLOYEE = "employee";
    public static final String COL_ID="id";
    public static final String COL_NAME="name";
    public static final String COL_ID_FIRM="idfirm";
    public static final String CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE " + TABLE_NAME_EMPLOYEE + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NAME+ " TEXT,"
                    + COL_ID_FIRM + " INTEGER)";

    public Employee(int mId, String mName) {
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
