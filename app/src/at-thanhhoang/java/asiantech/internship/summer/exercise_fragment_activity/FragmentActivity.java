package asiantech.internship.summer.exercise_fragment_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import asiantech.internship.summer.R;

public class FragmentActivity extends AppCompatActivity {
    public static final String TITLE_LOGIN = "Fragment Log In";
    public static final String TITLE_SIGN_UP = "Fragment Sign Up";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        mToolbar = findViewById(R.id.toolbar);

        LoginFragment mFragmentLogin = new LoginFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, mFragmentLogin);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void setTitleToolbar(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        int position = getSupportFragmentManager().getBackStackEntryCount();
        if (position == 1) {
            finish();
        } else if (position > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }
}
