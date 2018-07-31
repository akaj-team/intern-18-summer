package asiantech.internship.summer.recycler_view.timeline_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import asiantech.internship.summer.R;

public class ProgressBarViewHolder extends RecyclerView.ViewHolder {
    private final ProgressBar mProgressBar;

    ProgressBarViewHolder(View itemView) {
        super(itemView);
        this.mProgressBar = itemView.findViewById(R.id.progressBar);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
}
