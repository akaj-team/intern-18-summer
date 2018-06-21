package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import asiantech.internship.summer.R;

public class ActivityAndFragment extends AppCompatActivity {
    private LogInFragment mLogInFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_fragment);
        mLogInFragment = new LogInFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mLogInFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
