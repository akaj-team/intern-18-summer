package asiantech.internship.summer.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.TimelineItem;

public class ListItemAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private static final String TITLE = "Header";
    private static final String LIKE = " like ";
    private ArrayList<TimelineItem> mListItems;

    public ListItemAdapter(ArrayList<TimelineItem> listItem) {
        this.mListItems = listItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemview;
        switch (viewType) {
            case TYPE_LIST:
                itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
                return new ListViewHolder(itemview);
            case TYPE_HEAD:
                itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_item_view, viewGroup, false);
                return new HeaderViewHolder(itemview);
            default:
                itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        }
        return new ListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int Type = getItemViewType(position);
        if (Type == TYPE_LIST) {
            onBindViewHolder(((ListViewHolder) holder), position);
        } else if (Type == TYPE_HEAD) {
            ((HeaderViewHolder) holder).getmTvHeader().setText(TITLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void onBindViewHolder(ListViewHolder listViewHolder, int position) {
        TimelineItem timelineItem = mListItems.get(position);
        listViewHolder.getmTvName().setText(timelineItem.getAuthor().getName());
        listViewHolder.getmTvNameComment().setText(timelineItem.getAuthor().getName());
        listViewHolder.getmImgDish().setImageResource(timelineItem.getImage());
        listViewHolder.getmImgProfile().setImageResource(timelineItem.getAuthor().getAvatar());
        listViewHolder.getmTvDescription().setText(timelineItem.getDescription());
        listViewHolder.getmImgLike().setOnClickListener((View view) -> {
            timelineItem.Changenumberlike();
            boolean islike = timelineItem.ismIsLiked();
            if (islike) {
                listViewHolder.getmImgLike().setImageResource(R.drawable.ic_like);

            } else {
                listViewHolder.getmImgLike().setImageResource(R.drawable.ic_dislike);
            }
            listViewHolder.getmTvCountLike().setText((timelineItem.getmNumberLike()) + LIKE);

        });
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_LIST;
        }
    }
}
