package asiantech.internship.summer.file_storage.database.view.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.database.view.DatabaseActivity;

public class DeleteEmployeeDialogFragment extends DialogFragment {

    public static final String DELETE_EMPLOYEE_DIALOG_TAG = "DELETE_EMPLOYEE_DIALOG_TAG";
    DeleteEmployeeDialogListener mListener;
    private int mEmployeeId;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mEmployeeId = getArguments().getInt(DatabaseActivity.EMPLOYEE_ID_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_employee)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> mListener.deleteEmployee(mEmployeeId))
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel());
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DeleteEmployeeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    public interface DeleteEmployeeDialogListener {
        void deleteEmployee(int employeeId);
    }
}
