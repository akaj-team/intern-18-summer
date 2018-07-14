package asiantech.internship.summer.storage.adpater.database;

import asiantech.internship.summer.storage.model.database.models.Employee;

public interface OnImageItemClickListener {
        void onImageAddClick(int index);
        void onImageDeleteClick(Employee employee);
}
