package asiantech.internship.summer.filestorage.model;

public class Employee {
    private int mIdEmployee;
    private String mName;
    private String mPhoneNumber;
    private String mAddress;
    public Employee() {
    }

    public int getId() {
        return mIdEmployee;
    }

    public void setId(int Id) {
        this.mIdEmployee = Id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.mPhoneNumber = PhoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String Address) {
        this.mAddress = Address;
    }
}
