package asiantech.internship.summer.activity_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public class SignUpFragment extends Fragment {
    public static final String DATA_MAIL = "email_receive";
    public static final String DATA_PASSWORD = "password_receive";
    private EditText mEdtEmail, mEdtPassword, mEdtConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPassword = view.findViewById(R.id.edtPassword);
        mEdtConfirm = view.findViewById(R.id.edtConfirm);
        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.signup);

        btnSignUp.setOnClickListener(view1 -> {

            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();
            String confirmpassword = mEdtConfirm.getText().toString();
            if (email.equals("") || password.equals("") || confirmpassword.equals("")) {
                Toast.makeText(getActivity(), "Please output all data", Toast.LENGTH_SHORT).show();
            } else if (!isEmailValid(email) || !isValidPassword(password)) {
                Toast.makeText(getActivity(), "Please check information !" + email + "  " + password, Toast.LENGTH_LONG).show();
            } else if (password.equals(confirmpassword)) {
                LogInFragment logInFragment = new LogInFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email_receive", email);
                bundle.putString("password_receive", password);
                logInFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out, R.anim.slide_right_in, R.anim.slide_left_out);
                fragmentTransaction.replace(R.id.flHome, logInFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getActivity(), "Please check all data! it occured bug", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public boolean isEmailValid(String email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
