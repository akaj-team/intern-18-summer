package asiantech.internship.summer.filestorage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.filestorage.DatabaseHelper;
import asiantech.internship.summer.filestorage.model.Company;

import static asiantech.internship.summer.filestorage.DatabaseHelper.COLUMN_COMPANY_ADDRESS;
import static asiantech.internship.summer.filestorage.DatabaseHelper.COLUMN_COMPANY_ID;
import static asiantech.internship.summer.filestorage.DatabaseHelper.COLUMN_COMPANY_NAME;
import static asiantech.internship.summer.filestorage.DatabaseHelper.COLUMN_EMPLOYEE_NAME;
import static asiantech.internship.summer.filestorage.DatabaseHelper.TABLE_COMPANY;

public class CompanyDAO {
    //fiels
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private static final String TAG = "MyActivity";
    private String[] mAllColums = {
            COLUMN_COMPANY_ID,
            COLUMN_COMPANY_NAME,
            COLUMN_COMPANY_ADDRESS};

    public CompanyDAO(Context context) {
        mDbHelper = new DatabaseHelper(context);
        try {
            //open database
            mDatabase = mDbHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    public void close() {
        mDbHelper.close();
    }

    public void insertCompany(String name, String address) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, name);
        values.put(COLUMN_COMPANY_ADDRESS, address);
        long insertId = mDatabase.insert(TABLE_COMPANY, null, values);
        mDatabase.query(TABLE_COMPANY, mAllColums, COLUMN_COMPANY_ID + " = " + insertId, null, null, null, null);
    }

    private Company cursorToCompany(Cursor cursor) {
        Company company = new Company();
        company.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY_ID)));
        company.setName(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_NAME)));
        company.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_ADDRESS)));
        return company;

    }

    //selectCompany
    public List<Company> selectCompany() {
        Company company;
        List<Company> companyList = new ArrayList<>();
        String seletQuery = "Select * from " + TABLE_COMPANY;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(seletQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                company = cursorToCompany(cursor);
                companyList.add(company);
            }
        }
        Objects.requireNonNull(cursor).close();
        return companyList;
    }
}
