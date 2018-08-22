package asiantech.internship.summer.file_storage.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.file_storage.sqlite.database.model.Company;

public class CompanyDao {

    private Context mContext;

    public CompanyDao(Context context) {
        mContext = context;
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + Company.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(cursor.getInt(cursor.getColumnIndex(Company.COLUMN_ID)));
                company.setAddress(cursor.getString(cursor.getColumnIndex(Company.COLUMN_ADDRESS)));
                company.setName(cursor.getString(cursor.getColumnIndex(Company.COLUMN_NAME)));
                companies.add(company);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return companies;
    }

    public void insertCompany(String name, String address) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Company.COLUMN_NAME, name);
        values.put(Company.COLUMN_ADDRESS, address);

        sqLiteDatabase.insert(Company.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public String getCompanyName(int companyId) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getReadableDatabase();
        String sqlQuery = "SELECT " + Company.COLUMN_NAME +
                " FROM " + Company.TABLE_NAME +
                " WHERE " + Company.COLUMN_ID + " = " + companyId;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        String companyName = cursor.getString(0);
        cursor.close();
        return companyName;
    }
}
