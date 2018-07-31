package asiantech.internship.summer.viewpager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.adapter.ListItemAdapter;
import asiantech.internship.summer.recyclerview.adapter.OnClickListener;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

public class FavouriteFragment extends Fragment implements OnClickListener {

    private List<TimelineItem> mlistFavouriteItems;
    private FavouriteFragment.LikeClickListener mListener;
    private ListItemAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        RecyclerView mrecyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(linearLayoutManager);

        mlistFavouriteItems = new ArrayList<>();
        mlistFavouriteItems.add(null);
        mAdapter = new ListItemAdapter(mlistFavouriteItems, this);
        mrecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.custom_item);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(drawable));
        mrecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onClickListen(int position) {
        mlistFavouriteItems.get(position).changenumberlike();
        mAdapter.notifyItemChanged(position);
        mListener.onLikeCliked(position);
    }

    public void setListener(FavouriteFragment.LikeClickListener listener) {
        mListener = listener;
    }

    public interface LikeClickListener extends MainLikeClickListener {
        void onLikeCliked(int position);
    }

    public void addItem(TimelineItem timelineItem) {
        mlistFavouriteItems.add(1, timelineItem);
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(TimelineItem timelineItem) {
        mlistFavouriteItems.remove(timelineItem);
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mlistFavouriteItems.remove(position);
    }
}
