package asiantech.internship.summer.activity_fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import asiantech.internship.summer.R;
import asiantech.internship.summer.activity_fragment.fragments.InfoFragment;
import asiantech.internship.summer.activity_fragment.fragments.LoginFragment;
import asiantech.internship.summer.activity_fragment.fragments.SignUpFragment;
import asiantech.internship.summer.activity_fragment.model.Account;

public class ActivityFragmentActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener {

    private ArrayList<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_practice);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flRoot, new LoginFragment());
        fragmentTransaction.commit();

        mAccounts = new ArrayList<>();
        mAccounts.add(new Account("a@gmail.com", "12345678", "thit an thit"));
        mAccounts.add(new Account("b@gmail.com", "12345678", "thit an ca"));
    }

    @Override
    public boolean login(String email, String password) {
        boolean isHasAccount = false;
        for (Account account : mAccounts) {
            if (account.check(email, password)) {
                isHasAccount = true;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                InfoFragment infoFragment = new InfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString(InfoFragment.HELLO_KEY, account.getInfo());
                infoFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.flRoot, infoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
        }
        return isHasAccount;
    }

    @Override
    public void signUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.flRoot, new SignUpFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void addAccount(String email, String password) {
        String info = getResources().getString(R.string.hello);
        info = info.concat(email);
        mAccounts.add(new Account(email, password, info));

        Bundle bundle = new Bundle();
        bundle.putString(LoginFragment.DEFAULT_EMAIL_KEY, email);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

        fragmentTransaction.replace(R.id.flRoot, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
