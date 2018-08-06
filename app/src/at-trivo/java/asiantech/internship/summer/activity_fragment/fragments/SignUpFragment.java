package asiantech.internship.summer.activity_fragment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public class SignUpFragment extends Fragment {
    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private OnFragmentInteractionListener mListener;
    private EditText mEdtEmail, mEdtPassword, mEditRepeatPassword;
    private TextView mTvWarning;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPassword = view.findViewById(R.id.edtPassword);
        mEditRepeatPassword = view.findViewById(R.id.edtRepeatPassword);
        mTvWarning = view.findViewById(R.id.tvWarning);
        setUpSignUpAction(view);
        setTitle(R.string.sign_up);
        return view;
    }

    private void setUpSignUpAction(View view) {
        TextView tvSignUp = view.findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(view1 -> {
            String email = mEdtEmail.getText().toString();
            if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
                setWaringMessage(R.string.email_warning);
                return;
            }
            String password = mEdtPassword.getText().toString();
            if (!VALID_PASSWORD_REGEX.matcher(password).find()) {
                setWaringMessage(R.string.password_warning);
                return;
            }
            String repeatPassword = mEditRepeatPassword.getText().toString();
            if (!repeatPassword.equals(password)) {
                setWaringMessage(R.string.password_repeat_warning);
                return;
            }
            mListener.addAccount(email, password);
        });
    }

    private void setTitle(int idText) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Objects.requireNonNull(activity.getSupportActionBar()).setTitle(idText);
        }
    }

    private void setWaringMessage(int id) {
        mTvWarning.setText(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void addAccount(String email, String password);
    }
}
