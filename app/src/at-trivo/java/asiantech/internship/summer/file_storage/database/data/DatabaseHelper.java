package asiantech.internship.summer.file_storage.database.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.file_storage.database.data.model.Company;
import asiantech.internship.summer.file_storage.database.data.model.CompanyEmployees;
import asiantech.internship.summer.file_storage.database.data.model.Employee;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "test_storage_db";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Company.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(Employee.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(CompanyEmployees.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Company.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Employee.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CompanyEmployees.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + Company.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(cursor.getInt(cursor.getColumnIndex(Company.COLUMN_ID)));
                company.setCode(cursor.getString(cursor.getColumnIndex(Company.COLUMN_CODE)));
                company.setName(cursor.getString(cursor.getColumnIndex(Company.COLUMN_NAME)));
                companies.add(company);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return companies;
    }

    public void insertCompany(String code, String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Company.COLUMN_CODE, code);
        values.put(Company.COLUMN_NAME, name);

        sqLiteDatabase.insert(Company.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void insertEmployee(String name, String nickname, int companyId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Employee.COLUMN_NAME, name);
        values.put(Employee.COLUMN_NICKNAME, nickname);

        sqLiteDatabase.insert(Employee.TABLE_NAME, null, values);
        int employeeId = getJustInsertEmployeeId();
        signEmployeeToCompany(companyId, employeeId);
        sqLiteDatabase.close();
    }

    private int getJustInsertEmployeeId() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" Select last_insert_rowid() ", null);
        cursor.moveToFirst();
        int employeeId = cursor.getInt(0);
        cursor.close();
        return employeeId;
    }

    private void signEmployeeToCompany(int companyId, int employeeId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CompanyEmployees.COLUMN_ID_COMPANY, companyId);
        values.put(CompanyEmployees.COLUMN_ID_EMPLOYEE, employeeId);

        sqLiteDatabase.insert(CompanyEmployees.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public List<Employee> getEmployees(int companyId) {
        List<Employee> employees = new ArrayList<>();
        String sqlQuery = "SELECT * " +
                " FROM " + Employee.TABLE_NAME + " , " + CompanyEmployees.TABLE_NAME +
                " WHERE  " + Employee.COLUMN_ID + " = " + CompanyEmployees.COLUMN_ID_EMPLOYEE +
                " AND " + CompanyEmployees.COLUMN_ID_COMPANY + " = " + companyId;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(cursor.getColumnIndex(Employee.COLUMN_ID)));
                employee.setNickname(cursor.getString(cursor.getColumnIndex(Employee.COLUMN_NICKNAME)));
                employee.setName(cursor.getString(cursor.getColumnIndex(Employee.COLUMN_NAME)));
                employees.add(employee);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return employees;
    }

    public String getCompanyName(int companyId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sqlQuery = "SELECT " + Company.COLUMN_NAME +
                " FROM " + Company.TABLE_NAME +
                " WHERE " + Company.COLUMN_ID + " = " + companyId;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        String companyName = cursor.getString(0);
        cursor.close();
        return companyName;
    }

    public void deleteEmployee(int employeeId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sqlQuery="DELETE FROM "+Employee.TABLE_NAME+
                " WHERE "+Employee.COLUMN_ID +" = "+employeeId;
        sqLiteDatabase.execSQL(sqlQuery);
        sqLiteDatabase.close();
    }
}
