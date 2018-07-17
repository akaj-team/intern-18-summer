package asiantech.internship.summer.storage.model.database.models;

public class Employee {
    private int mIdEmployee;
    private int mIdCompanyEmployee;
    private String mNameEmployee;

    public Employee() {
    }

    public int getIdEmployee() {
        return mIdEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.mIdEmployee = idEmployee;
    }

    public void setIdCompanyEmployee(int idCompanyEmployee) {
        this.mIdCompanyEmployee = idCompanyEmployee;
    }

    public String getNameEmployee() {
        return mNameEmployee;
    }

    public void setNameEmployee(String mNameEmployee) {
        this.mNameEmployee = mNameEmployee;
    }

    @Override
    public String toString() {
        return "Company [idEmploy = " + mIdEmployee + ",idCompany = " +  mIdCompanyEmployee + ", name = " + mNameEmployee + "]";
    }
}
