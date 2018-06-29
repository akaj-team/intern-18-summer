package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

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

public class LogInFragment extends Fragment {
    static final String DATA_RECEIVE_EMAIL = "email";
    static final String DATA_RECEIVE_PASSWORD = "password";
    private EditText mEdtEmail;
    private EditText mEdtPassword;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_login, container, false);
        TextView tvSignUp = contentView.findViewById(R.id.tvSignUp);
        TextView tvLogIn = contentView.findViewById(R.id.tvLogIn);
        mEdtEmail = contentView.findViewById(R.id.edtEmail);
        mEdtPassword = contentView.findViewById(R.id.edtPassword);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Log in");

        tvSignUp.setOnClickListener(view -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                    R.anim.slide_left_in, R.anim.slide_right_out);
            transaction.replace(R.id.fragmentContainer, signUpFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });

        tvLogIn.setOnClickListener((View view) -> {
            String email = mEdtEmail.getText().toString();
            String password =mEdtPassword.getText().toString();

            if (email.equals("") || password.equals("")) {
                Toast.makeText(contentView.getContext(), "Please fill full information sign up!", Toast.LENGTH_SHORT).show();
            } else {

                if (CheckAccount.validateMail(email)) {
                    Toast.makeText(contentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
                } else {
                    if (CheckAccount.checkPassword(password)) {
                        Toast.makeText(contentView.getContext(), "Sorry!!! Password must be 6 or more character and don't have specail character ", Toast.LENGTH_SHORT).show();
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
        });
        return contentView;
    }
}
