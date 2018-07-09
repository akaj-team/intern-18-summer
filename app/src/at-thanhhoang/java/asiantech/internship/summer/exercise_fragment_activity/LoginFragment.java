package asiantech.internship.summer.exercise_fragment_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class LoginFragment extends Fragment {
    public static final String KEY_MAIL = "KEY_MAIL";
    public static final String KEY_PASS = "KEY_PASS";

    private TextView mTvSignUp, mTvLogin;
    private EditText mEdtMailLogin, mEdtPassWordLogin;
    private String mEmailReceive, mPassReceive;
    private SignUpFragment mFragmentSignUp = new SignUpFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
        Objects.requireNonNull(fragmentActivity).setTitleToolbar(FragmentActivity.TITLE_LOGIN);

        initView(view);
        addListener();
        return view;
    }

    @SuppressLint("ResourceType")
    private void addListener() {
        mTvSignUp.setOnClickListener(view -> {
            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in,
                    R.anim.slide_right_out);
            transaction.replace(R.id.flContainer, mFragmentSignUp);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mTvLogin.setOnClickListener(view -> {
            String email = mEdtMailLogin.getText().toString();
            String password = mEdtPassWordLogin.getText().toString();

            if (email.equals("") || password.equals("")) {
                Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else if (CheckValid.isEmailValid(email) && CheckValid.isPasswordValid(password)) {
                if (email.equals("thanhhoang@gmail.com") && password.equals("abc123") ||
                        email.equals(mEmailReceive) && password.equals(mPassReceive)) {
                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra(KEY_MAIL, email);
                    intent.putExtra(KEY_PASS, password);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Not found data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Format error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        mTvSignUp = view.findViewById(R.id.tvSignUpFirst);
        mTvLogin = view.findViewById(R.id.tvLogin);
        mEdtMailLogin = view.findViewById(R.id.edtEmail);
        mEdtPassWordLogin = view.findViewById(R.id.edtPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            mEmailReceive = args.getString(SignUpFragment.MAIL_PASS);
            mPassReceive = args.getString(SignUpFragment.PASSWORD_PASS);

            mEdtMailLogin.setText(mEmailReceive);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
