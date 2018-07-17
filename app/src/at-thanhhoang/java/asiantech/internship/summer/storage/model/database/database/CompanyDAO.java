package asiantech.internship.summer.storage.model.database.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import asiantech.internship.summer.storage.model.database.models.Company;

public class CompanyDAO {
    @SuppressLint("StaticFieldLeak")
    private static CompanyDAO mInstance = null;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;

    private CompanyDAO(Context context) {
        mDbHelper = new DBHelper(context);
    }

    public static CompanyDAO getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CompanyDAO(context);
        }
        return mInstance;
    }

    public void insertCompany(String nameCompany) {
        mDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_NAME, nameCompany);
        mDatabase.insert(DBHelper.COMPANY_TABLE_NAME, null, values);
        mDatabase.close();
    }

    public ArrayList<Company> getAllCompany() {
        mDatabase = mDbHelper.getWritableDatabase();
        ArrayList<Company> companyList = new ArrayList<>();
        Cursor res = mDatabase.rawQuery("select * from " + DBHelper.COMPANY_TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Company company = new Company();
            company.setIdCompany(res.getInt(0));
            company.setNameCompany(res.getString(1));
            companyList.add(company);
            res.moveToNext();
        }
        res.close();
        mDatabase.close();
        return companyList;
    }
}
