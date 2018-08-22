package asiantech.internship.summer.file_storage.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import asiantech.internship.summer.file_storage.sqlite.database.model.Company;
import asiantech.internship.summer.file_storage.sqlite.database.model.Employee;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "test_storage_db";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Company.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(Employee.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Company.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Employee.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
