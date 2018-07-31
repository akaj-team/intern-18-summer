package asiantech.internship.summer.timeline.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.timeline.model.TimelineItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private final List<TimelineItem> mTimelineList;
    private final ClickViewListener mListener;
    private final int mIsFragment;

    public TimelineAdapter(List<TimelineItem> mTimelineList, int mIsFragment, ClickViewListener mListener) {
        this.mTimelineList = mTimelineList;
        this.mIsFragment = mIsFragment;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_timeline,
                parent, false);
        return new TimelineViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TimelineItem timelineItem = mTimelineList.get(position);

        holder.mImgAvatar.setImageResource(timelineItem.getImageAvatar());
        holder.mTvUsername.setText(timelineItem.getUsername());
        holder.mImgPostFood.setImageResource(timelineItem.getImagePost());
        holder.mTvDescriptionFood.setText(Html.fromHtml(timelineItem.getDescriptionFood()), TextView.BufferType.SPANNABLE);

        holder.addListener(timelineItem);
    }

    @Override
    public int getItemCount() {
        return mTimelineList.size();
    }

    @SuppressLint("SetTextI18n")
    private void setStateLike(ImageView imgLike, TextView tvCountLikes) {
        imgLike.setImageResource(R.drawable.ic_like_red);
        tvCountLikes.setText("1 likes");
    }

    @SuppressLint("SetTextI18n")
    private void setStateDisLike(ImageView imgLike, TextView tvCountLike) {
        imgLike.setImageResource(R.drawable.ic_like_white);
        tvCountLike.setText("0 likes");
    }

    public interface ClickViewListener {
        void onCLickLike(int position);

        void onClickDislike(int position, boolean status);
    }

    class TimelineViewHolder extends ViewHolder {

        final private CircleImageView mImgAvatar;
        final private TextView mTvUsername;
        final private ImageView mImgPostFood;
        final private ImageView mImgLike;
        final private TextView mTvCountLike;
        final private TextView mTvDescriptionFood;

        private TimelineViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mImgPostFood = itemView.findViewById(R.id.imgPostFood);
            mImgLike = itemView.findViewById(R.id.imgLike);
            mTvCountLike = itemView.findViewById(R.id.tvCountLike);
            mTvDescriptionFood = itemView.findViewById(R.id.tvDescriptionFood);
        }

        private void addListener(TimelineItem timelineItem) {
            if (timelineItem.isStateLikes()) {
                setStateLike(mImgLike, mTvCountLike);
            } else {
                setStateDisLike(mImgLike, mTvCountLike);
            }

            mImgLike.setOnClickListener(view -> {
                if (mIsFragment == 0) {
                    if (timelineItem.isStateLikes()) {
                        setStateDisLike(mImgLike, mTvCountLike);
                        timelineItem.setStateLikes(!timelineItem.isStateLikes());
                    } else {
                        setStateLike(mImgLike, mTvCountLike);
                        timelineItem.setStateLikes(!timelineItem.isStateLikes());
                    }
                } else if (mIsFragment == 1) {
                    if (timelineItem.isStateLikes()) {
                        setStateDisLike(mImgLike, mTvCountLike);
                        mListener.onClickDislike(getAdapterPosition(), true);
                        timelineItem.setStateLikes(!timelineItem.isStateLikes());
                    } else {
                        setStateLike(mImgLike, mTvCountLike);
                        mListener.onCLickLike(getAdapterPosition());
                        timelineItem.setStateLikes(!timelineItem.isStateLikes());
                    }
                } else if (mIsFragment == 2) {
                    mListener.onClickDislike(getAdapterPosition(), false);
                }
            });
        }
    }
}
