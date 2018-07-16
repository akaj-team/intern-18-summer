package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Company.db";
    public static final int VERSION = 1;
    //colums of company table
    public static final String TABLE_COMPANY = "Company.db";
    public static final String COLUMN_COMPANY_ID = "ID";
    public static final String COLUMN_COMPANY_NAME = "NAME";
    public static final String COLUMN_COMPANY_ADDRESS = "ADDRESS";
    public static final String COLUMN_COMPANY_PHONENUMBER = "PHONE";

    //colums of company table
    public static final String TABLE_EMPLOYEE = "Employee.db";
    public static final String COLUMN_EMPLOYEE_ID = "ID";
    public static final String COLUMN_COMPANY_KEY_ID = "KEY_ID";
    public static final String COLUMN_EMPLOYEE_NAME = "NAME";
    public static final String COLUMN_EMPLOYEE_ADDRESS = "ADDRESS";
    public static final String COLUMN_EMPLOYEE_PHONENUMBER = "PHONE";

    //SQL statement of company table create.
    private static final String SQL_CREATE_TABLE_COMPANY = " CREATE TABLE " + TABLE_COMPANY + "("
            + COLUMN_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY_NAME + " TEXT NOT NULL, "
            + COLUMN_COMPANY_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_COMPANY_PHONENUMBER + " TEXT NOT NULL "
            + ");";
    //SQL statement of employee table create.
    private static final String SQL_CREATE_TABLE_EMPLOYEE = " CREATE TABLE " + TABLE_EMPLOYEE + "("
            + COLUMN_EMPLOYEE_ID + " INTERGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY_KEY_ID + " INTERGET NOT NULL,"
            + COLUMN_EMPLOYEE_NAME +  " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_PHONENUMBER + " TEXT NOT NULL "
            + ");";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_COMPANY);
        db.execSQL(SQL_CREATE_TABLE_EMPLOYEE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //clear all data
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_COMPANY);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        //create table
        onCreate(db);
    }
    public Cursor getAllData(){
        String seletQuery = "Select * from " + TABLE_COMPANY + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(seletQuery, null);
        return cursor;

    }
}
