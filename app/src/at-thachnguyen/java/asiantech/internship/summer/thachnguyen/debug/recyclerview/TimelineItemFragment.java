package asiantech.internship.summer.thachnguyen.debug.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.Owner;
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;

import static asiantech.internship.summer.R.layout.fragment_timeline_item;

public class TimelineItemFragment extends Fragment {
    private ArrayList<TimelineItem> mTimelines;
    private TimelineAdapter mTimelineAdapter;
    private ProgressBar mProgressBarLoad;
    private boolean mIsScrolling = false;
    private int mCurrentItems, mTotalItemCount, mScrollOutItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TimelineAdapter.OnLikeClickListener mOnLikeClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragment_timeline_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimelines = new ArrayList<>();
        RecyclerView recyclerViewTimeline = view.findViewById(R.id.recyclerViewTimeline);

        mTimelineAdapter = new TimelineAdapter(getContext(), mTimelines, mOnLikeClickListener);

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
                    loadMoreTimeLine();
                }
            }
        });

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadRefreshTimeLine);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadRefreshTimeLine();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnLikeClickListener = (TimelineAdapter.OnLikeClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    private void createListTimeLine() {
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner(getName(i % 5), getAvatar(i % 5));
            mTimelines.add(new TimelineItem(owner, randomImageFood(), getDescription(i), 0, false));
            mTimelineAdapter.notifyDataSetChanged();
        }
    }

    private void loadMoreTimeLine() {
        mProgressBarLoad.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            createListTimeLine();
            mProgressBarLoad.setVisibility(View.GONE);
        }, 5000);
    }

    private void loadRefreshTimeLine() {
        mTimelines.clear();
        createListTimeLine();
        new Handler().postDelayed(() ->
                        mSwipeRefreshLayout.setRefreshing(false),
                2000);
    }

    private String getName(int i) {
        String[] arrayNames = Objects.requireNonNull(getContext()).getResources().getStringArray(R.array.name);
        return arrayNames[i];
    }

    private int getAvatar(int i) {
        String imgName = "img_avt" + i;
        return Objects.requireNonNull(getActivity()).getResources().getIdentifier(imgName, "drawable", Objects.requireNonNull(getContext()).getPackageName());
    }

    private String getDescription(int i) {
        String[] arrayDescriptions = Objects.requireNonNull(getContext()).getResources().getStringArray(R.array.description);
        return arrayDescriptions[i];
    }

    private int randomImageFood() {
        Random rand = new Random();
        int rndN = rand.nextInt(22) + 1;
        String imgName = "img_" + "food" + rndN;
        return Objects.requireNonNull(getContext()).getResources().getIdentifier(imgName, "drawable", getContext().getPackageName());
    }

    public void setLike(TimelineItem timelineItem) {
        mTimelines.get(mTimelines.indexOf(timelineItem)).setmLike(mTimelines.get(mTimelines.indexOf(timelineItem)).getmLike());
        mTimelineAdapter.notifyDataSetChanged();
    }

    public void messageFavourite(TimelineItem timelineItem) {
        if (timelineItem.ismCheckLike()) {
            Toast.makeText(getContext(), "You liked " + timelineItem.getmOwner().getmName() + "'s post", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "You unliked " + timelineItem.getmOwner().getmName() + "'s post", Toast.LENGTH_SHORT).show();
        }
    }
}
