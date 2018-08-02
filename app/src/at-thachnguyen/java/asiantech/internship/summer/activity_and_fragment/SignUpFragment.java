package asiantech.internship.summer.activity_and_fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class SignUpFragment extends Fragment {
    private static final String DATA_RECEIVE_EMAIL = "mail";
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private TextView mTvSignUp;
    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(contentView);
        mToolbar.setTitle("Sign up");
        mTvSignUp.setOnClickListener(view -> {
            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();
            String confirmPassword = mEdtConfirmPassword.getText().toString();
            signUp(contentView, email, password, confirmPassword);
        });
        return contentView;
    }

    private void init(View contentView) {
        mEdtEmail = contentView.findViewById(R.id.edtEmail);
        mEdtPassword = contentView.findViewById(R.id.edtPassword);
        mEdtConfirmPassword = contentView.findViewById(R.id.edtConfirmPassword);
        mTvSignUp = contentView.findViewById(R.id.tvSignUp);
        mToolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
    }

    private void signUp(View contentView, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword) || password.equals("") || confirmPassword.equals("") || CheckAccount.checkPassword(password)) {
            Toast.makeText(contentView.getContext(), "Sorry!!! Password or confirm password is incorrect!", Toast.LENGTH_SHORT).show();
        } else {
            if (CheckAccount.validateMail(email) || email.equals("")) {
                Toast.makeText(contentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
            } else {
                LoginFragment mLoginFragment = new LoginFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString(DATA_RECEIVE_EMAIL, email);
                mLoginFragment.setArguments(mBundle);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out,
                        R.anim.slide_right_in, R.anim.slide_left_out)
                        .add(R.id.fragmentContainer, mLoginFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
