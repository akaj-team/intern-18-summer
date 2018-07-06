package asiantech.internship.summer.recycler_view.timeline_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class TimelineViewHolder extends RecyclerView.ViewHolder {

    private final ImageView mImgAuthor;
    private final TextView mTvAuthorName;
    private final ImageView mImgTimeline;
    private final ImageView mImgHeart;
    private final TextView mTvLoveNumber;
    private final TextView mTvDescription;

    private Integer mPosition;
    private TimeLineViewHolderListener mListener;

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public ImageView getImgAuthor() {
        return mImgAuthor;
    }

    public TextView getTvAuthorName() {
        return mTvAuthorName;
    }

    public ImageView getImgTimeline() {
        return mImgTimeline;
    }

    public ImageView getImgHeart() {
        return mImgHeart;
    }

    public TextView getTvLoveNumber() {
        return mTvLoveNumber;
    }

    public TextView getTvDescription() {
        return mTvDescription;
    }

    TimelineViewHolder(View itemView, TimeLineViewHolderListener listener) {
        super(itemView);
        mListener = listener;
        mImgAuthor = itemView.findViewById(R.id.imgAuthor);
        mTvAuthorName = itemView.findViewById(R.id.tvAuthorName);
        mImgTimeline = itemView.findViewById(R.id.imgTimeline);
        mImgHeart = itemView.findViewById(R.id.imgHeart);
        mTvLoveNumber = itemView.findViewById(R.id.tvLoveNumber);
        mTvDescription = itemView.findViewById(R.id.tvDescription);
        mImgHeart.setOnClickListener(view -> mListener.onHeartImageClick(mPosition));
    }

    public interface TimeLineViewHolderListener {
        void onHeartImageClick(int position);
    }
}
