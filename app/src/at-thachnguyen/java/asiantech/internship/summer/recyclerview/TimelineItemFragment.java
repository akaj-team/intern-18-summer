package asiantech.internship.summer.recyclerview;

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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.model.Owner;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

import static asiantech.internship.summer.R.layout.fragment_timeline_item;

public class TimelineItemFragment extends Fragment {
    private ArrayList<TimelineItem> mTimelines;
    private TimelineAdapter mTimelineAdapter;
    private ProgressBar mProgressBarLoad;
    private boolean mIsScrolling = false;
    private int mCurrentItems;
    private int mTotalItemCount;
    private int mScrollOutItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewTimeline;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimelines = new ArrayList<>();
        mTimelineAdapter = new TimelineAdapter(getContext(), mTimelines, position -> {
            if (mTimelines.get(position).ismIsLike()){
                mTimelines.get(position).setmLike(mTimelines.get(position).getmLike()-1);
            }
            else {
                mTimelines.get(position).setmLike(mTimelines.get(position).getmLike()+1);
            }
            mTimelines.get(position).setmIsLike(!mTimelines.get(position).ismIsLike());
            mTimelineAdapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_timeline_item, container, false);
        mRecyclerViewTimeline = view.findViewById(R.id.recyclerViewTimeline);
        mProgressBarLoad = view.findViewById(R.id.progressBarLoad);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTimeline.setAdapter(mTimelineAdapter);
        mRecyclerViewTimeline.setLayoutManager(layoutManager);

        mRecyclerViewTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void createListTimeLines() {
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner(getName(i % 5), getAvatar(i % 5));
            mTimelines.add(new TimelineItem(owner, randomImageFood(), getDescription(i), 0, false));
            mTimelineAdapter.notifyDataSetChanged();
        }
    }

    private void loadMoreTimeLine() {
        mProgressBarLoad.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            createListTimeLines();
            mProgressBarLoad.setVisibility(View.GONE);
        }, 5000);
    }

    private void loadRefreshTimeLine() {
        mTimelines.clear();
        createListTimeLines();
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
}
