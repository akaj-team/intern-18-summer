package asiantech.internship.summer.recycler_view.timeline_recycler_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recycler_view.model.Timeline;

public class TimelineAdapter extends RecyclerView.Adapter {

    private final List<Timeline> mDataset;
    private Context mContext;
    private TimelineViewHolder.TimeLineViewHolderListener mListener;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESS_BAR = 0;
    private final int VIEW_TYPE_HEADER = 2;

    public TimelineAdapter(List<Timeline> dataset, Context context, TimelineViewHolder.TimeLineViewHolderListener listener) {
        mContext = context;
        mDataset = dataset;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_timeline, parent, false);
                return new TimelineViewHolder(view, mListener);
            case VIEW_TYPE_PROGRESS_BAR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_progress_bar, parent, false);
                return new ProgressBarViewHolder(view);
            case VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_header, parent, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_timeline, parent, false);
        }
        return new TimelineViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case VIEW_TYPE_ITEM:
                onBindViewHolder(((TimelineViewHolder) holder), position);
                break;
            case VIEW_TYPE_PROGRESS_BAR:
                ((ProgressBarViewHolder) holder).getProgressBar().setIndeterminate(true);
                break;
        }
    }

    private void onBindViewHolder(TimelineViewHolder timelineViewHolder, int position) {
        timelineViewHolder.setPosition(position);
        Timeline timeline = mDataset.get(position);
        timelineViewHolder.getImgAuthor().setImageResource(timeline.getAuthor().getProfileImageId());
        timelineViewHolder.getTvAuthorName().setText(timeline.getAuthor().getName());
        timelineViewHolder.getImgTimeline().setImageResource(timeline.getTimelineImageId());
        StringBuilder stringBuilder = new StringBuilder().append(timeline.getLoveNumber()).append(mContext.getResources().getString(R.string.like));
        timelineViewHolder.getTvLoveNumber().setText(stringBuilder);
        timelineViewHolder.getTvDescription().setText(timeline.getDescription());
        boolean isLoved = timeline.isLoved();
        if (isLoved) {
            timelineViewHolder.getImgHeart().setImageResource(R.drawable.ic_heart_filled);
        } else {
            timelineViewHolder.getImgHeart().setImageResource(R.drawable.ic_heart);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position) == null) {
            if (position == 0) {
                return VIEW_TYPE_HEADER;
            }
            return VIEW_TYPE_PROGRESS_BAR;
        }
        return VIEW_TYPE_ITEM;
    }
}
