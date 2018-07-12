package asiantech.internship.summer.file_storage.database.view.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import asiantech.internship.summer.R;
import asiantech.internship.summer.file_storage.database.view.DatabaseActivity;

public class AddEmployeeDialogFragment extends DialogFragment {

    public static final String ADD_EMPLOYEE_DIALOG_TAG = "add employee tag";
    private AddEmployeeDialogListener mListener;
    private EditText mEdtEmployeeName;
    private EditText mEdtEmployeeNickname;
    private int mCompanyId;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCompanyId = getArguments().getInt(DatabaseActivity.COMPANY_ID_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_add_employee, null);
        mEdtEmployeeName = view.findViewById(R.id.edtEmployeeName);
        mEdtEmployeeNickname = view.findViewById(R.id.edtEmployeeNickname);

        builder.setView(view)
                .setPositiveButton(R.string.ok, (dialogInterface, i)
                        -> mListener.addEmployee(mEdtEmployeeName.getText().toString(), mEdtEmployeeNickname.getText().toString(), mCompanyId))
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel());
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddEmployeeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    public interface AddEmployeeDialogListener {
        void addEmployee(String name, String nickname, int companyId);
    }
}
