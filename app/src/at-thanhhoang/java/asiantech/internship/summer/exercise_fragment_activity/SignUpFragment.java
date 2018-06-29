package asiantech.internship.summer.exercise_fragment_activity;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class SignUpFragment extends Fragment {

    public static final String MAIL_PASS = "sent mail";
    public static final String PASSWORD_PASS = "sent password";
    private EditText mEdtEmail, mEdtPassword, mEdtConfirmPassWord;
    private TextView mTvSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
        Objects.requireNonNull(fragmentActivity).setTitleToolbar(FragmentActivity.TITLE_SIGN_UP);

        initView(view);
        addListener();
        return view;
    }

    @SuppressLint("ResourceType")
    private void addListener() {
        mTvSignUp.setOnClickListener(view -> {
            String mail = mEdtEmail.getText().toString().trim();
            String password = mEdtPassword.getText().toString();
            String confirmPassword = mEdtConfirmPassWord.getText().toString();

            if (mail.equals("") || password.equals("") || confirmPassword.equals("")) {
                Toast.makeText(getActivity(), "Please enter all fields ", Toast.LENGTH_SHORT).show();
            } else if (CheckValid.isEmailValid(mail) && CheckValid.isPasswordValid(password) && CheckValid.isPasswordValid(confirmPassword))
            {
                if (password.equals(confirmPassword)) {
                    LoginFragment mFragmentLogin = new LoginFragment();

                    Bundle args = new Bundle();
                    args.putString(MAIL_PASS, mail);
                    args.putString(PASSWORD_PASS, password);
                    mFragmentLogin.setArguments(args);

                    FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out, R.anim.slide_right_in,
                            R.anim.slide_left_out);
                    transaction.replace(R.id.flContainer, mFragmentLogin);
                    transaction.addToBackStack(null);

                    transaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Passwords must match!!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(), "Error format!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        mEdtEmail = view.findViewById(R.id.edtEmailSignUp);
        mEdtPassword = view.findViewById(R.id.edtPasswordSignUp);
        mEdtConfirmPassWord = view.findViewById(R.id.edtConfirmPasswordSignUp);

        mTvSignUp = view.findViewById(R.id.tvSignUpLast);
    }

    @Override
    public void onStart() {
        super.onStart();
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
