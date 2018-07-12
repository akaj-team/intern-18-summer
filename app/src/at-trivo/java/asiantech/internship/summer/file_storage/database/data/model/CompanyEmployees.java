package asiantech.internship.summer.file_storage.database.data.model;

public class CompanyEmployees {
    public static final String TABLE_NAME = "company_employees";

    public static final String COLUMN_ID_COMPANY = "id_company";
    public static final String COLUMN_ID_EMPLOYEE= "id_employee";

    public static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME + " ( "
                    + COLUMN_ID_COMPANY + " INTEGER ,"
                    + COLUMN_ID_EMPLOYEE + " INTEGER )";
}
