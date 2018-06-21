package asiantech.internship.summer.thachnguyen.debug;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import asiantech.internship.summer.R;

public class SignUpFragment extends Fragment {
    public static final String DATA_RECEIVE_EMAIL="mail";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        EditText edtEmail=mContentView.findViewById(R.id.edtEmail);
        EditText edtPassword=mContentView.findViewById(R.id.edtPassword);
        EditText edtConfirmPassword=mContentView.findViewById(R.id.edtConfirmPassword);
        TextView tvSignUp=mContentView.findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(view -> {
            String email=edtEmail.getText().toString();
            String password=edtPassword.getText().toString();
            String confirmPassword=edtConfirmPassword.getText().toString();

            if(email.equals("")||password.equals("")||confirmPassword.equals("")){
                Toast.makeText(mContentView.getContext(), "Please fill full information sign up!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(!password.equals(confirmPassword)){
                    Toast.makeText(mContentView.getContext(), "Sorry!!! Password and confirm password are not the same!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!CheckAccount.checkEmail(email)){
                        Toast.makeText(mContentView.getContext(), "Sorry!!! Your email is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!CheckAccount.checkPassword(password)){
                            Toast.makeText(mContentView.getContext(), "Sorry!!! Password must be 6 or more character", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            LogInFragment mLogInFragment = new LogInFragment ();
                            Bundle mBundle = new Bundle();
                            mBundle.putString(DATA_RECEIVE_EMAIL, email);
                            mLogInFragment.setArguments(mBundle);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, mLogInFragment )
                                    .commit();
                        }
                    }
                }
            }
        });


        return mContentView;
    }
}

