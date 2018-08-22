package asiantech.internship.summer.file_storage.sqlite.database.model;

public class Company {
    public static final String TABLE_NAME = "companies";

    public static final String COLUMN_ID = "company_id";
    public static final String COLUMN_NAME = "company_name";
    public static final String COLUMN_ADDRESS = "company_code";

    public static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_ADDRESS + " TEXT ,"
                    + COLUMN_NAME + " TEXT )";

    private int id;
    private String mName;
    private String mAddress;

    public Company() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
