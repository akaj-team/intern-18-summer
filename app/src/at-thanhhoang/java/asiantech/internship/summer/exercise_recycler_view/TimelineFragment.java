package asiantech.internship.summer.exercise_recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.exercise_recycler_view.adapter.TimelineAdapter;
import asiantech.internship.summer.exercise_recycler_view.model.TimelineItem;

@SuppressLint("ValidFragment")
public class TimelineFragment extends Fragment {

    private TypedArray mArrayImageAvatar;
    private String[] mArrayUsername;
    private TypedArray mArrayImageFood;
    private String[] mArrayDescription;
    private int mIsCheck;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoad;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TimelineAdapter mAdapterTimeline;

    private List<TimelineItem> mTimelineList;

    private boolean mIsLoading = true;
    private int mLastVisibleItem, mTotalItemCount, mVisibleItemCount;

    private SendObjectTimeline mSot;

    @SuppressLint("ValidFragment")
    public TimelineFragment(int isCheck) {
        this.mIsCheck = isCheck;
    }

    public interface SendObjectTimeline {
        void likesTimeline(TimelineItem timelineItem);

        void dislikeTimeline(TimelineItem timelineItem);

        void removeItemFavorite(TimelineItem timelineItem);

        void removeAllDataFavourite();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mSot = (SendObjectTimeline) getActivity();
        } catch (Exception e) {
            Log.d("Error", "onAttach: ");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        intView(view);
        addListener();
        return view;
    }

    private void intView(View view) {
        mArrayImageAvatar = getResources().obtainTypedArray(R.array.imageAvatar);
        mArrayUsername = getResources().getStringArray(R.array.username_array);
        mArrayImageFood = getResources().obtainTypedArray(R.array.imageFood);
        mArrayDescription = new String[]{
                "Wow, that is delicious!",
                "That is amazing!",
                "The tastes great, where did you buy it?",
                "The food at that restaurant is out of this World",
                "It’s so yummy, where did you get the recipe?",
                "I'm in heaven",
                "It’s so fresh",
        };
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mProgressBarLoad = view.findViewById(R.id.progressBarLoadMore);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);

        mTimelineList = createTimeLineList();
    }


    private void addListener() {
        if (mIsCheck == 2) {
            mTimelineList.clear();
        }
        mAdapterTimeline = new TimelineAdapter(mTimelineList, mIsCheck, new TimelineAdapter.ClickViewListener() {
            @Override
            public void onCLickLike(int position) {
                likesListener(position);
            }

            @Override
            public void onClickDislike(int position, boolean status) {
                if (status) {
                    dislikesListener(position);
                } else {
                    removeDataListener(position);
                }
            }

            @Override
            public void onShowPositionItem(int position) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapterTimeline);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mIsLoading = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (mIsCheck == 0 || mIsCheck == 1) {
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
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mIsCheck == 0 || mIsCheck == 1) {
                fetchData();
                if (mIsCheck == 1) {
                    mSot.removeAllDataFavourite();
                }
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }

    private void fetchData() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> {
            mTimelineList.clear();
            mTimelineList.addAll(createTimeLineList());
            mAdapterTimeline.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }, 2000);
    }

    private void onLoadMore() {
        mProgressBarLoad.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            mTimelineList.addAll(createTimeLineList());
            Log.d("size", "run: " + mTimelineList.size());
            mAdapterTimeline.notifyDataSetChanged();
            mProgressBarLoad.setVisibility(View.GONE);
        }, 2000);
    }

    private void likesListener(int position) {
        TimelineItem timelineItem = mTimelineList.get(position);
        mSot.likesTimeline(timelineItem);
    }

    private void dislikesListener(int position) {
        TimelineItem timelineItem = mTimelineList.get(position);
        mSot.dislikeTimeline(timelineItem);
    }

    private void removeDataListener(int position) {
        TimelineItem timelineItem = mTimelineList.get(position);
        mSot.removeItemFavorite(timelineItem);
        mTimelineList.remove(timelineItem);
        mAdapterTimeline.notifyDataSetChanged();
    }

    private int getRandomImageAvatar() {
        final Random rand = new Random();
        final int rndInt = rand.nextInt(mArrayImageAvatar.length());
        return mArrayImageAvatar.getResourceId(rndInt, 0);
    }

    private String getRandomUsername() {
        return mArrayUsername[new Random().nextInt(mArrayUsername.length)];
    }

    private int getRandomImageFood() {
        final Random rand = new Random();
        final int rndInt = rand.nextInt(mArrayImageFood.length());
        return mArrayImageFood.getResourceId(rndInt, 0);
    }

    private String getRandomDescription() {
        return mArrayDescription[new Random().nextInt(mArrayDescription.length)];
    }

    private ArrayList<TimelineItem> createTimeLineList() {
        ArrayList<TimelineItem> timelineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int idImageAvatar = getRandomImageAvatar();
            String username = getRandomUsername();
            int idImageFood = getRandomImageFood();
            String description = getRandomDescription();

            String des = "<font color='black'>" + username + "</font>";

            timelineList.add(new TimelineItem(idImageAvatar, username, idImageFood, false, des + " " + description));
        }
        return timelineList;
    }

    public void receivedDataFavourite(TimelineItem timelineItem) {
        mTimelineList.add(0, timelineItem);
        mAdapterTimeline.notifyDataSetChanged();
    }

    public void removeDataFavourite(TimelineItem timelineItem) {
        mTimelineList.remove(timelineItem);
        mAdapterTimeline.notifyDataSetChanged();
    }

    public void moveStateLikeTimeLine(TimelineItem timelineItem) {
        int position = mTimelineList.lastIndexOf(timelineItem);
        mTimelineList.get(position).setStateHeart(false);
        mAdapterTimeline.notifyDataSetChanged();
    }

    public void removeAll() {
        mTimelineList.clear();
        mAdapterTimeline.notifyDataSetChanged();
    }
}
