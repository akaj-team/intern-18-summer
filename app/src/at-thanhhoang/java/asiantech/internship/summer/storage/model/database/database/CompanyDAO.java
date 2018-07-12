package asiantech.internship.summer.storage.model.database.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import asiantech.internship.summer.storage.model.database.models.Company;

public class CompanyDAO {
    public static final String TAG = Company.class.getSimpleName();
    private static CompanyDAO mInstance = null;

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumn = {DBHelper.COLUMN_COMPANY_ID, DBHelper.COLUMN_COMPANY_NAME};

    public CompanyDAO(Context context) {
        mDbHelper = new DBHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public static CompanyDAO getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CompanyDAO(context);
        }
        return mInstance;
    }

    public void insertCompany(Company company) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_NAME, company.getmNameCompany());
        mDatabase.insert(DBHelper.COMPANY_TABLE_NAME, null, values);
    }

    public ArrayList<Company> getAllCompany() {
        ArrayList<Company> companyList = new ArrayList<>();
        Cursor res = mDatabase.rawQuery("select * from " + DBHelper.COMPANY_TABLE_NAME, null);
        res.moveToFirst();
        Log.d(TAG, "getAllCompany: xxxx " + res.getCount());

        while (!res.isAfterLast()) {
            Company company = new Company(res.getInt(0), res.getString(1));
            companyList.add(company);
        }

        Log.d(TAG, "getAllCompany: " + companyList);
        res.close();
        return companyList;
    }
}
