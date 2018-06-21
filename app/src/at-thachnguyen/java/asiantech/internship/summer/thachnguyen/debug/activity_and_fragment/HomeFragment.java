package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class HomeFragment extends Fragment {
    private TextView tvEmail,tvPassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        tvEmail=mContentView.findViewById(R.id.tvEmail);
        tvPassword=mContentView.findViewById(R.id.tvPassword);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            tvPassword.setText(mBundle.getString(LogInFragment.DATA_RECEIVE_PASSWORD));
            tvEmail.setText(mBundle.getString(LogInFragment.DATA_RECEIVE_EMAIL));
        }
    }
}

