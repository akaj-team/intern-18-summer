package asiantech.internship.summer.filestorage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.filestorage.DatabaseHelper;
import asiantech.internship.summer.filestorage.model.Employee;

import static asiantech.internship.summer.filestorage.DatabaseHelper.TABLE_EMPLOYEE;

public class EmployeeDAO {
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = {
            DatabaseHelper.COLUMN_EMPLOYEE_ID,
            DatabaseHelper.COLUMN_EMPLOYEE_KEY_ID,
            DatabaseHelper.COLUMN_EMPLOYEE_NAME,
            DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS,
            DatabaseHelper.COLUMN_EMPLOYEE_PHONENUMBER,
    };

    public EmployeeDAO(Context context) {
        mDbHelper = new DatabaseHelper(context);
        // open database
        try {
            mDatabase = mDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        mDbHelper.close();
    }

    public void insertEmployee(String name, String address, String phonenumber, int key_company) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS, address);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_PHONENUMBER, phonenumber);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_KEY_ID, key_company);
        long insertId = mDatabase.insert(DatabaseHelper.TABLE_EMPLOYEE, null, values);
        mDatabase.query(DatabaseHelper.TABLE_EMPLOYEE, mAllColumns, DatabaseHelper.COLUMN_EMPLOYEE_ID + " = " + insertId, null, null, null, null);

    }

    private Employee cursorToEmployee(Cursor cursor) {

        Employee employee = new Employee();
        employee.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ID)));
        employee.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_NAME)));
        employee.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS)));
        employee.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_PHONENUMBER)));
        //get company by id
        /*lay nhan vien cua company*/
        return employee;
    }

    public void deleteEmployee(int employeeId) {
        mDatabase.delete(DatabaseHelper.TABLE_EMPLOYEE, DatabaseHelper.COLUMN_EMPLOYEE_ID + " = " + employeeId, null);
    }

    public List<Employee> getEmployeesOfCompany(int companyId) {
        List<Employee> employeeList = new ArrayList<>();
        String selectQuery = "Select * from " + TABLE_EMPLOYEE + " where " + DatabaseHelper.COLUMN_EMPLOYEE_KEY_ID + " = " + companyId;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmployee(cursor);
            employeeList.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        return employeeList;
    }
}
