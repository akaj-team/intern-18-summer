package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private final Context mContext;
    private final ArrayList<TimelineItem> mListTimeline;
    private final OnLikeClickListener mLikeClickListener;

    TimelineAdapter(Context mContext, ArrayList<TimelineItem> mListTimeline, OnLikeClickListener mLikeClickListener) {
        this.mContext = mContext;
        this.mListTimeline = mListTimeline;
        this.mLikeClickListener = mLikeClickListener;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_timeline, parent, false);
        return new TimelineViewHolder(layout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        holder.mCircleImageViewAvatar.setImageResource(mListTimeline.get(position).getmOwner().getmAvatar());
        holder.mTvNameOwner.setText(mListTimeline.get(position).getmOwner().getmName());
        holder.mImgFood.setImageResource(mListTimeline.get(position).getmImage());
        holder.mTvLike.setText(mListTimeline.get(position).getmLike() + " like");
        holder.mTvNameOwnerPost.setText(mListTimeline.get(position).getmOwner().getmName());
        holder.mTvDescription.setText(mListTimeline.get(position).getmDescription());
        holder.mImgLike.setOnClickListener(view -> {
            if (holder.mImgLike.getDrawable().getConstantState() == mContext.getResources().getDrawable(R.drawable.ic_unlike).getConstantState()) {
                mListTimeline.get(position).setmLike(mListTimeline.get(position).getmLike() + 1);
                holder.mTvLike.setText(mListTimeline.get(position).getmLike() + " like");
                holder.mImgLike.setImageResource(R.drawable.ic_like);
            } else {
                mListTimeline.get(position).setmLike(mListTimeline.get(position).getmLike() - 1);
                holder.mTvLike.setText(mListTimeline.get(position).getmLike() + " like");
                holder.mImgLike.setImageResource(R.drawable.ic_unlike);
            }
            mLikeClickListener.onLikeClickListener(position);
        });
    }

    @Override
    public int getItemCount() {
        return mListTimeline.size();
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView mCircleImageViewAvatar;
        final TextView mTvNameOwner;
        final ImageView mImgFood;
        final TextView mTvLike;
        final TextView mTvNameOwnerPost;
        final TextView mTvDescription;
        final ImageView mImgLike;

        TimelineViewHolder(View itemView) {
            super(itemView);
            mCircleImageViewAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            mTvNameOwner = itemView.findViewById(R.id.tvNameOwner);
            mImgFood = itemView.findViewById(R.id.imgFood);
            mTvLike = itemView.findViewById(R.id.tvLike);
            mTvNameOwnerPost = itemView.findViewById(R.id.tvNameOwnerPost);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mImgLike = itemView.findViewById(R.id.imgLike);
        }
    }

    public interface OnLikeClickListener {
        void onLikeClickListener(int position);
    }
}
