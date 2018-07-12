package asiantech.internship.summer.storage.model.database.models;

import java.io.Serializable;

public class Company implements Serializable{
    private int mIdCompany;
    private String mNameCompany;

    public Company(String mNameCompany) {
        this.mNameCompany = mNameCompany;
    }

    public Company(int mIdCompany, String mNameCompany) {
        this.mIdCompany = mIdCompany;
        this.mNameCompany = mNameCompany;
    }

    public int getmIdCompany() {
        return mIdCompany;
    }

    public void setmIdCompany(int mIdCompany) {
        this.mIdCompany = mIdCompany;
    }

    public String getmNameCompany() {
        return mNameCompany;
    }

    public void setmNameCompany(String mNameCompany) {
        this.mNameCompany = mNameCompany;
    }
}
