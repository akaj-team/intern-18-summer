package asiantech.internship.summer.file_storage.sqlite.database.model;

public class Employee {

    public static final String TABLE_NAME = "employees";

    public static final String COLUMN_ID = "employee_id";
    public static final String COLUMN_COMPANY_ID = "pk_company_id";
    public static final String COLUMN_NAME = "employee_name";
    public static final String COLUMN_GENDER = "employee_gender";

    public static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_COMPANY_ID + " INTEGER ,"
                    + COLUMN_NAME + " TEXT ,"
                    + COLUMN_GENDER + " INTEGER )";

    private int mId;
    private String mName;
    private boolean mGender;

    public Employee() {
    }

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

    public boolean isMan() {
        return mGender;
    }

    public void setGender(boolean gender) {
        this.mGender = gender;
    }
}
