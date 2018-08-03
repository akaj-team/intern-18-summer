package asiantech.internship.summer.activity_and_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class LoginFragment extends Fragment {
    static final String DATA_RECEIVE_EMAIL = "email";
    static final String DATA_RECEIVE_PASSWORD = "password";
    private Toolbar mToolbar;
    private TextView mTvSignUp;
    private TextView mTvLogin;
    private EditText mEdtEmail;
    private EditText mEdtPassword;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_login, container, false);
        init(contentView);
        mToolbar.setTitle("Log in");
        mTvSignUp.setOnClickListener(view -> signUp());
        mTvLogin.setOnClickListener((View view) -> {
            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();
            login(contentView, email, password);
        });
        return contentView;
    }

    private void init(View contentView) {
        mToolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        mTvSignUp = contentView.findViewById(R.id.tvSignUp);
        mTvLogin = contentView.findViewById(R.id.tvLogin);
        mEdtEmail = contentView.findViewById(R.id.edtEmail);
        mEdtPassword = contentView.findViewById(R.id.edtPassword);
    }

    private void signUp() {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out);
        transaction.add(R.id.fragmentContainer, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void login(View contentView, String email, String password) {
        if (email.equals("") || CheckAccount.validateMail(email)) {
            Toast.makeText(contentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
        } else {
            if (CheckAccount.checkPassword(password) || password.equals("")) {
                Toast.makeText(contentView.getContext(), "Sorry!!! Password is incorrect ", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                Bundle bundleReceive = new Bundle();
                bundleReceive.putString(DATA_RECEIVE_EMAIL, email);
                bundleReceive.putString(DATA_RECEIVE_PASSWORD, password);
                intent.putExtras(bundleReceive);
                startActivity(intent);
            }
        }
    }
}
