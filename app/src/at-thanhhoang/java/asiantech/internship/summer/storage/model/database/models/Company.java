package asiantech.internship.summer.storage.model.database.models;

public class Company {
    private int mIdCompany;
    private String mNameCompany;

    public Company() {
    }

    public int getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(int idCompany) {
        this.mIdCompany = idCompany;
    }

    public String getNameCompany() {
        return mNameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.mNameCompany = nameCompany;
    }

    @Override
    public String toString() {
        return "Company [id=" + mIdCompany + ", name=" + mNameCompany + "]";
    }
}
