package asiantech.internship.summer.storage.model.database.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import asiantech.internship.summer.storage.model.database.models.Employee;

public class EmployeeDAO {
    public static final String TAG = EmployeeDAO.class.getSimpleName();

    private Context mContext;

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumn = {DBHelper.COLUMN_EMPLOYEE_ID, DBHelper.COLUMN_EMPLOYEE_NAME};

    public EmployeeDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void insertEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put("name", employee.getmNameEmployee());
        mDatabase.insert("company", null, values);
    }

    public void deleteEmployee(Integer id) {
        mDatabase.delete(DBHelper.EMPLOYEE_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Employee> getAllEmployee() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        Cursor res = mDatabase.rawQuery("select * from " + DBHelper.EMPLOYEE_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Employee employee = new Employee(res.getInt(0), res.getString(1));
            employeeList.add(employee);
        }
        return employeeList;
    }
}
