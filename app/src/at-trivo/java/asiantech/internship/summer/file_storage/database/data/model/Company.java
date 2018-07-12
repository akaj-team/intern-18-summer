package asiantech.internship.summer.file_storage.database.data.model;

public class Company {
    public static final String TABLE_NAME = "companies";

    public static final String COLUMN_ID = "company_id";
    public static final String COLUMN_NAME = "company_name";
    public static final String COLUMN_CODE = "company_code";

    public static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_CODE + " TEXT ,"
                    + COLUMN_NAME + " TEXT )";

    private int id;
    private String mName;
    private String mCode;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company(){

    }
}
