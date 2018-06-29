package asiantech.internship.summer.exercise_recycler_view.adapter;

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
import asiantech.internship.summer.exercise_recycler_view.model.TimelineItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private final List<TimelineItem> mTimelineList;
    private final ClickViewListener mListener;
    private static int sIsClickLike = 1;

    public interface ClickViewListener{
        void onImageLikeClick(int position);
    }

    public TimelineAdapter(List<TimelineItem> mTimelineList,  ClickViewListener mListener) {
        this.mTimelineList = mTimelineList;
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
        holder.mTvDescriptionFood.setText(Html.fromHtml(timelineItem.getDescriptionFood()),TextView.BufferType.SPANNABLE);
        holder.mImgLike.setOnClickListener(view -> {
            if(sIsClickLike == 0){
                holder.mImgLike.setBackgroundResource(R.drawable.ic_like_white);
                holder.mTvCountLike.setText(sIsClickLike + " likes");
                sIsClickLike = 1;
            }else{
                holder.mImgLike.setBackgroundResource(R.drawable.ic_like_red);
                holder.mTvCountLike.setText(sIsClickLike + " likes");
                sIsClickLike = 0;
            }
            mListener.onImageLikeClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mTimelineList.size();
    }

    class TimelineViewHolder extends ViewHolder {

        private CircleImageView mImgAvatar;
        private TextView mTvUsername;
        private ImageView mImgPostFood;
        private ImageView mImgLike;
        private TextView mTvCountLike;
        private TextView mTvDescriptionFood;

        private TimelineViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mImgPostFood = itemView.findViewById(R.id.imgPostFood);
            mImgLike = itemView.findViewById(R.id.imgLike);
            mTvCountLike = itemView.findViewById(R.id.tvCountLike);
            mTvDescriptionFood = itemView.findViewById(R.id.tvDescriptionFood);
        }
    }
}
