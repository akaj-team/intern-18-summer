package asiantech.internship.summer.thachnguyen.debug.viewpager;

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

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.TimelineAdapter;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;

@SuppressWarnings("CollectionAddedToSelf")
public class FavouriteFragment extends Fragment {
    private TimelineAdapter mTimelineAdapter;
    private final ArrayList<TimelineItem> mTimelines = new ArrayList<>();
    private OnUnlikeClickListener mOnUnlikeClickListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFavourite = view.findViewById(R.id.recyclerViewFavourite);
        mTimelineAdapter = new TimelineAdapter(getContext(), mTimelines, timelineItem -> {
            mTimelines.remove(timelineItem);
            mTimelineAdapter.notifyDataSetChanged();
            mOnUnlikeClickListener.onUnlikeClickListener(timelineItem);
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFavourite.setLayoutManager(layoutManager);
        recyclerViewFavourite.setAdapter(mTimelineAdapter);
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
        mTimelines.removeAll(mTimelines);
        mTimelineAdapter.notifyDataSetChanged();
    }

    interface OnUnlikeClickListener {
        void onUnlikeClickListener(TimelineItem timelineItem);
    }

    public interface RemoveAllList {
        void removeAllList();
    }
}
