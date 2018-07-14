package asiantech.internship.summer.storage.model.database.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import asiantech.internship.summer.storage.model.database.models.Employee;

public class EmployeeDAO {
    @SuppressLint("StaticFieldLeak")
    private static EmployeeDAO mInstance = null;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;

    private EmployeeDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
    }

    public static EmployeeDAO getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EmployeeDAO(context);
        }
        return mInstance;
    }

    public void insertEmployee(int companyIndex, String employeeName) {
        mDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_EMPLOYEE_ID, companyIndex);
        values.put(DBHelper.COLUMN_EMPLOYEE_NAME, employeeName);
        mDatabase.insert(DBHelper.EMPLOYEE_TABLE_NAME, null, values);
        mDatabase.close();
    }

    public void deleteEmployee(int idEmployee) {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.execSQL("DELETE FROM " + DBHelper.EMPLOYEE_TABLE_NAME + " WHERE "
                + DBHelper.COLUMN_EMPLOYEE_ID + " = " + idEmployee);
        mDatabase.close();
    }

    public ArrayList<Employee> getAllEmployee(int companyIndex) {
        mDatabase = mDbHelper.getWritableDatabase();
        ArrayList<Employee> employeeList = new ArrayList<>();
        Cursor res = mDatabase.rawQuery("select * from " + DBHelper.EMPLOYEE_TABLE_NAME + " WHERE " + DBHelper.COLUMN_COMPANY_EMPLOYEE_ID + " = " + companyIndex, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Employee employee = new Employee();
            employee.setIdEmployee(res.getInt(0));
            employee.setIdCompanyEmployee(res.getInt(1));
            employee.setNameEmployee(res.getString(2));
            employeeList.add(employee);
            res.moveToNext();
        }
        Toast.makeText(mContext, "size " + employeeList.size(), Toast.LENGTH_SHORT).show();
        res.close();
        mDatabase.close();
        return employeeList;
    }
}
