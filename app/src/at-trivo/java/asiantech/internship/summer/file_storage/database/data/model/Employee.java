package asiantech.internship.summer.file_storage.database.data.model;

public class Employee {

    public static final String TABLE_NAME = "employees";

    public static final String COLUMN_ID = "employee_id";
    public static final String COLUMN_NAME = "employee_name";
    public static final String COLUMN_NICKNAME = "employee_nickname";

    public static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_NAME + " TEXT ,"
                    + COLUMN_NICKNAME + " TEXT )";

    private int mId;
    private String mName;
    private String mNickname;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        this.mNickname = nickname;
    }

    public Employee() {
    }
}
