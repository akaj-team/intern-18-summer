package asiantech.internship.summer.storage.model.database.models;

import java.io.Serializable;

public class Employee implements Serializable{
    private int mIdEmployee;
    private String mNameEmployee;

    public Employee(String mNameEmployee) {
        this.mNameEmployee = mNameEmployee;
    }

    public Employee(int mIdEmployee, String mNameEmployee) {
        this.mIdEmployee = mIdEmployee;
        this.mNameEmployee = mNameEmployee;
    }

    public int getmIdEmployee() {
        return mIdEmployee;
    }

    public void setmIdEmployee(int mIdEmployee) {
        this.mIdEmployee = mIdEmployee;
    }

    public String getmNameEmployee() {
        return mNameEmployee;
    }

    public void setmNameEmployee(String mNameEmployee) {
        this.mNameEmployee = mNameEmployee;
    }
}
