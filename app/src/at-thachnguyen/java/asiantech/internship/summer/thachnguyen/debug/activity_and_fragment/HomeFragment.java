package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Objects;
import asiantech.internship.summer.R;

public class HomeFragment extends Fragment {
    private TextView tvEmail, tvPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        tvEmail = contentView.findViewById(R.id.tvEmail);
        tvPassword = contentView.findViewById(R.id.tvPassword);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        return contentView;
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

