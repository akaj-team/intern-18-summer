package asiantech.internship.summer.filestorage.model;

public class Company {
    private int mId;
    private String mName;
    private String mAddress;

    public Company(String mName, String mAddress) {
        this.mName = mName;
        this.mAddress = mAddress;
    }

    public int getId() {
        return mId;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String Address) {
        this.mAddress = Address;
    }

}
