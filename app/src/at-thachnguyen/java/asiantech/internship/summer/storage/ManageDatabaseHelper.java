package asiantech.internship.summer.storage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.storage.model.Employee;
import asiantech.internship.summer.storage.model.Firm;

public class ManageDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private String mTableName;
    private String mCreateTable;

    ManageDatabaseHelper(Context context, String mTableName, String mCreateTable) {
        super(context, mTableName, null, DATABASE_VERSION);
        this.mTableName = mTableName;
        this.mCreateTable = mCreateTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + mTableName + "'");
        onCreate(db);
    }

    public void insertFirm(String firmName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Firm.COL_NAME, firmName);
        db.insert(mTableName, null, values);
        db.close();
    }


    public void insertEmployee(String employeeName, int idFirm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Employee.COL_NAME, employeeName);
        values.put(Employee.COL_ID_FIRM, idFirm);
        db.insert(mTableName, null, values);
        db.close();
    }

    public List<Firm> getAllFirms() {
        List<Firm> firms = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + mTableName;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                firms.add(new Firm(cursor.getInt(0), cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return firms;
    }

    public List<Employee> getEmployees(int idFirm) {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + mTableName + " WHERE " + Employee.COL_ID_FIRM + " = " + "'" + idFirm + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                employees.add(new Employee(cursor.getInt(0), cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return employees;
    }


    public void deleteEmployee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(mTableName, Employee.COL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
