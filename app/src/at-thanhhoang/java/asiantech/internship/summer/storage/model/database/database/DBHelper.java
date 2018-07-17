package asiantech.internship.summer.storage.model.database.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "MyDBName.db";
    private static final int  DATABASE_VERSION = 1;

    public static final String COMPANY_TABLE_NAME = "company";
    private static final String COLUMN_COMPANY_ID = "id";
    public static final String COLUMN_COMPANY_NAME = "company_name";

    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COLUMN_COMPANY_EMPLOYEE_ID = COLUMN_COMPANY_ID;
    public static final String COLUMN_EMPLOYEE_ID = "id_employee";
    public static final String COLUMN_EMPLOYEE_NAME = "employee_name";

    private static final String CREATE_TABLE_COMPANY = "CREATE TABLE " + COMPANY_TABLE_NAME + "("
            + COLUMN_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY_NAME + " TEXT NOT NULL "
            + ");";

    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + EMPLOYEE_TABLE_NAME + "("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY_EMPLOYEE_ID + " INTEGER NOT NULL, "
            + COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL "
            + ");";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPANY);
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "Upgrading the database from version " + i + " to " + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COMPANY_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
