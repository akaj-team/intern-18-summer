package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

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
    static final String DATA_RECEIVE_EMAIL="mail";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        EditText edtEmail=contentView.findViewById(R.id.edtEmail);
        EditText edtPassword=contentView.findViewById(R.id.edtPassword);
        EditText edtConfirmPassword=contentView.findViewById(R.id.edtConfirmPassword);
        TextView tvSignUp=contentView.findViewById(R.id.tvSignUp);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Sign up");

        tvSignUp.setOnClickListener(view -> {
            String email=edtEmail.getText().toString();
            String password=edtPassword.getText().toString();
            String confirmPassword=edtConfirmPassword.getText().toString();

            if(email.equals("")||password.equals("")||confirmPassword.equals("")){
                Toast.makeText(contentView.getContext(), "Please fill full information sign up!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(!password.equals(confirmPassword)){
                    Toast.makeText(contentView.getContext(), "Sorry!!! Password and confirm password are not the same!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(CheckAccount.validateMail(email)){
                        Toast.makeText(contentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(CheckAccount.checkPassword(password)){
                            Toast.makeText(contentView.getContext(), "Sorry!!! Password must be 6 or more character and don't have special character", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            LogInFragment mLogInFragment = new LogInFragment ();
                            Bundle mBundle = new Bundle();
                            mBundle.putString(DATA_RECEIVE_EMAIL, email);
                            mLogInFragment.setArguments(mBundle);
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out,
                                    R.anim.slide_right_in, R.anim.slide_left_out)
                                    .replace(R.id.fragmentContainer, mLogInFragment )
                                    .commit();
                        }
                    }
                }
            }
        });
        return contentView;
    }
}
