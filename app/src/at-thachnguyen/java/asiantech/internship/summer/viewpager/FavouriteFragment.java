package asiantech.internship.summer.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.TimelineAdapter;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

@SuppressWarnings("CollectionAddedToSelf")
public class FavouriteFragment extends Fragment {
    private List<TimelineItem> mTimelines;
    private TimelineAdapter mTimelineAdapter;
    private RecyclerView mRecyclerViewFavourite;
    private OnUnlikeClickListener mOnUnlikeClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimelines = new ArrayList<>();
        mTimelineAdapter = new TimelineAdapter(getContext(), (ArrayList<TimelineItem>) mTimelines, timelineItem -> {
            mTimelines.remove(timelineItem);
            mTimelineAdapter.notifyDataSetChanged();
            mOnUnlikeClickListener.onUnlikeClickListener(timelineItem);
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favourite, container, false);
        mRecyclerViewFavourite = view.findViewById(R.id.recyclerViewFavourite);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFavourite.setLayoutManager(layoutManager);
        mRecyclerViewFavourite.setAdapter(mTimelineAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnUnlikeClickListener = (OnUnlikeClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public void display(TimelineItem timelineItem) {
        if (timelineItem.getmLike() == 1) {
            mTimelines.add(0, timelineItem);
        } else {
            mTimelines.remove(timelineItem);
        }
        mTimelineAdapter.notifyDataSetChanged();
    }

    public void removeAll() {
        mTimelines.clear();
        mTimelineAdapter.notifyDataSetChanged();
    }

    interface OnUnlikeClickListener {
        void onUnlikeClickListener(TimelineItem timelineItem);
    }

    public interface OnPullRefreshRecyclerView {
        void refresh();
    }
}
