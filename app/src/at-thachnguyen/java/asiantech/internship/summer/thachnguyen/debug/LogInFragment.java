package asiantech.internship.summer.thachnguyen.debug;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class LogInFragment extends Fragment {
    public static final String DATA_RECEIVE_EMAIL = "email";
    public static final String DATA_RECEIVE_PASSWORD = "password";
    private EditText edtEmail;
    private EditText edtPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_login, container, false);
        TextView tvSignUp = mContentView.findViewById(R.id.tvSignUp);
        TextView tvLogIn = mContentView.findViewById(R.id.tvLogIn);
        edtEmail = mContentView.findViewById(R.id.edtEmail);
        edtPassword = mContentView.findViewById(R.id.edtPassword);


        tvSignUp.setOnClickListener(view -> {
            Activity mActivity = getActivity();
            SignUpFragment mSignUpFragment = new SignUpFragment();
            FragmentTransaction mTransaction = mActivity.getFragmentManager().beginTransaction();
            mTransaction.replace(R.id.fragmentContainer, mSignUpFragment);
            mTransaction.addToBackStack(null);
            mTransaction.commit();
        });

        tvLogIn.setOnClickListener(view -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            if (email.equals("") || password.equals("")) {
                Toast.makeText(mContentView.getContext(), "Please fill full information sign up!", Toast.LENGTH_SHORT).show();
            } else {

                if (!CheckAccount.checkEmail(email)) {
                    Toast.makeText(mContentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!CheckAccount.checkPassword(password)) {
                        Toast.makeText(mContentView.getContext(), "Sorry!!! Password must be 6 or more character", Toast.LENGTH_SHORT).show();
                    } else {
                        HomeFragment mHomeFragment = new HomeFragment();
                        Bundle mBundleReceive = new Bundle();
                        mBundleReceive.putString(DATA_RECEIVE_EMAIL, email);
                        mBundleReceive.putString(DATA_RECEIVE_PASSWORD, password);
                        mHomeFragment.setArguments(mBundleReceive);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, mHomeFragment)
                                .commit();
                    }
                }
            }
        });

        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle mBundleSend = getArguments();
        if (mBundleSend != null) {
            edtEmail.setText(mBundleSend.getString(SignUpFragment.DATA_RECEIVE_EMAIL));
        }
    }
}

