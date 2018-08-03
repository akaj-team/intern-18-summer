package asiantech.internship.summer.activity_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class LogInFragment extends Fragment {
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private String mPasswordReceive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.login);
        TextView tvSignUp = view.findViewById(R.id.tvSignUp);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPassword = view.findViewById(R.id.edtPassword);

        tvSignUp.setOnClickListener(view1 -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
            fragmentTransaction.replace(R.id.flHome, signUpFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        btnLogin.setOnClickListener(
                view12 -> {
                    String email = mEdtEmail.getText().toString();
                    String password = mEdtPassword.getText().toString();
                    if (email.equals("") || password.equals("")) {
                        Toast.makeText(view12.getContext(), "Please fill out most of information", Toast.LENGTH_SHORT).show();
                    } else if (!isValidEmail(email)) {
                        Toast.makeText(view12.getContext(), "email occured issue !", Toast.LENGTH_SHORT).show();
                    } else if ((password.length() < 6)) {
                        Toast.makeText(view12.getContext(), "Password is not format", Toast.LENGTH_SHORT).show();
                    } else if (email.equals("lam@gmail.com") && password.equals("01010101") ||
                            password.equals(mPasswordReceive)) {
                        Intent intent = new Intent(getActivity(), ResultActivity.class);
                        intent.putExtra("email_receive", email);
                        intent.putExtra("password_receive", password);
                        startActivity(intent);
                    } else {
                        Toast.makeText(view12.getContext(), "input again password", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String email_receive = bundle.getString(SignUpFragment.DATA_MAIL);
            mPasswordReceive = bundle.getString(SignUpFragment.DATA_PASSWORD);
            mEdtEmail.setText(email_receive);
        }
    }
}
