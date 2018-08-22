package asiantech.internship.summer.file_storage.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.file_storage.sqlite.database.model.Employee;

public class EmployeeDao {

    private Context mContext;

    public EmployeeDao(Context context) {
        mContext = context;
    }

    public void insertEmployee(String name, boolean gender, int companyId) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Employee.COLUMN_NAME, name);
        values.put(Employee.COLUMN_GENDER, gender ? 1 : 0);
        values.put(Employee.COLUMN_COMPANY_ID, companyId);

        sqLiteDatabase.insert(Employee.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public List<Employee> getEmployees(int companyId) {
        List<Employee> employees = new ArrayList<>();
        String sqlQuery = "SELECT * " + " FROM " + Employee.TABLE_NAME +
                " WHERE  " + Employee.COLUMN_COMPANY_ID + " = " + companyId;
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(cursor.getColumnIndex(Employee.COLUMN_ID)));
                employee.setGender(cursor.getInt(cursor.getColumnIndex(Employee.COLUMN_GENDER)) > 0);
                employee.setName(cursor.getString(cursor.getColumnIndex(Employee.COLUMN_NAME)));
                employees.add(employee);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return employees;
    }

    public void deleteEmployee(int employeeId) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(mContext).getWritableDatabase();
        String sqlQuery = "DELETE FROM " + Employee.TABLE_NAME +
                " WHERE " + Employee.COLUMN_ID + " = " + employeeId;
        sqLiteDatabase.execSQL(sqlQuery);
        sqLiteDatabase.close();
    }
}
