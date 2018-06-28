package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.Owner;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;

import static asiantech.internship.summer.R.layout.fragment_timeline_item;


public class TimelineItemFragment extends Fragment {
    private ProgressBar mProgressBarLoad;
    private ArrayList<TimelineItem> mListTimelines;
    private TimelineAdapter mTimelineAdapter;
    private boolean mIsScrolling = false;
    private int mCurrentItems, mScrollOutItems, mTotalItemCount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragment_timeline_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListTimelines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner(RecyclerViewActivity.getName(i % 5), RecyclerViewActivity.getAvatar(i % 5));
            mListTimelines.add(new TimelineItem(owner, RecyclerViewActivity.randomImageFood("food", 22), RecyclerViewActivity.getDescription(i), 0));
        }
        RecyclerView recyclerViewTimeline = view.findViewById(R.id.recyclerViewTimeline);
        mTimelineAdapter = new TimelineAdapter(getContext(), mListTimelines);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTimeline.setAdapter(mTimelineAdapter);
        recyclerViewTimeline.setLayoutManager(layoutManager);
        mProgressBarLoad = view.findViewById(R.id.progressBarLoad);
        recyclerViewTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItems = layoutManager.getChildCount();
                mTotalItemCount = layoutManager.getItemCount();
                mScrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (mIsScrolling && (mCurrentItems + mScrollOutItems == mTotalItemCount)) {
                    mIsScrolling = false;
                    fetchData();
                }
            }
        });


    }

    private void fetchData() {
        mProgressBarLoad.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                Owner owner = new Owner(RecyclerViewActivity.getName(i % 5), RecyclerViewActivity.getAvatar(i % 5));
                mListTimelines.add(new TimelineItem(owner, RecyclerViewActivity.randomImageFood("food", 22), RecyclerViewActivity.getDescription(i), 0));
                mTimelineAdapter.notifyDataSetChanged();
                mTimelineAdapter.notifyDataSetChanged();
                mProgressBarLoad.setVisibility(View.GONE);
            }
        }, 1000);
    }
}
