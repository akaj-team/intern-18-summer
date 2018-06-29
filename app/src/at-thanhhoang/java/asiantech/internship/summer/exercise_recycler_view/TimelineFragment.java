package asiantech.internship.summer.exercise_recycler_view;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.exercise_recycler_view.adapter.TimelineAdapter;
import asiantech.internship.summer.exercise_recycler_view.model.TimelineItem;

public class TimelineFragment extends Fragment {

    private static TypedArray sArrayImageAvatar;
    private static String[] sArrayUsername;
    private static TypedArray sArrayImageFood;
    private static String[] sArrayDescription;

    private ProgressBar mProgressBarLoad;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TimelineAdapter mAdapterTimeline;

    private List<TimelineItem> mTimelineList;

    private boolean mIsLoading = true;
    private int mLastVisibleItem, mTotalItemCount, mVisibleItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sArrayImageAvatar = getResources().obtainTypedArray(R.array.imageAvatar);
        sArrayUsername = getResources().getStringArray(R.array.username_array);
        sArrayImageFood = getResources().obtainTypedArray(R.array.imageFood);
        sArrayDescription = new String[]{
                "Wow, that is delicious!",
                "That is amazing!",
                "The tastes great, where did you buy it?",
                "The food at that restaurant is out of this World",
                "It’s so yummy, where did you get the recipe?",
                "I'm in heaven",
                "It’s so fresh",
        };

        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        mProgressBarLoad = getView().findViewById(R.id.progressBarLoadMore);
        mSwipeRefreshLayout = getView().findViewById(R.id.swipe_container);

        mTimelineList = (TimelineItem.createTimeLineList());

        mAdapterTimeline = new TimelineAdapter(mTimelineList, position -> Toast.makeText(getActivity(), "position item: " + position, Toast.LENGTH_SHORT).show());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapterTimeline);
        mAdapterTimeline.notifyDataSetChanged();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mIsLoading = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    mVisibleItemCount = layoutManager.getChildCount();
                    mTotalItemCount = layoutManager.getItemCount();
                    mLastVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    Log.d("value", "mTotalItemCount: " + mTotalItemCount + "mLastVisibleItem: " + mLastVisibleItem);

                    if (mIsLoading && (mVisibleItemCount + mLastVisibleItem) == mTotalItemCount) {
                        mIsLoading = false;
                        onLoadMore();
                    }
                }
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(this::fetchData);
    }


    private void fetchData() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> {
            mTimelineList.clear();
            mTimelineList.addAll(TimelineItem.createTimeLineList());
            mAdapterTimeline.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }, 2000);
    }

    private synchronized void onLoadMore() {
        mProgressBarLoad.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            mTimelineList.addAll(TimelineItem.createTimeLineList());
            Log.d("size", "run: " + mTimelineList.size());
            mAdapterTimeline.notifyDataSetChanged();
            mProgressBarLoad.setVisibility(View.GONE);
        }, 2000);
    }

    public synchronized static int getRandomImageAvatar() {
        final Random rand = new Random();
        final int rndInt = rand.nextInt(sArrayImageAvatar.length());
        return sArrayImageAvatar.getResourceId(rndInt, 0);
    }

    public synchronized static String getRandomUsername() {
        return sArrayUsername[new Random().nextInt(sArrayUsername.length)];
    }

    public synchronized static int getRandomImageFood() {
        final Random rand = new Random();
        final int rndInt = rand.nextInt(sArrayImageFood.length());
        return sArrayImageFood.getResourceId(rndInt, 0);
    }

    public synchronized static String getRandomDescription() {
        return sArrayDescription[new Random().nextInt(sArrayDescription.length)];
    }
}
