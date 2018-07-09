package asiantech.internship.summer.recycler_view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recycler_view.model.Timeline;
import asiantech.internship.summer.recycler_view.model.TimelineCreator;
import asiantech.internship.summer.recycler_view.timeline_recycler_view.TimelineAdapter;
import asiantech.internship.summer.recycler_view.timeline_recycler_view.TimelineViewHolder;

public class TimeLineFragment extends Fragment
        implements TimelineViewHolder.TimeLineViewHolderListener {

    private static final int TIME_DELAY = 5000;
    protected List<Timeline> mDataSet;
    private LinearLayoutManager mLayoutManager;
    protected TimelineAdapter mAdapter;
    private RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsLoading;
    private TimelineFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataSet = new ArrayList<>();
        setUpDataSet();
    }

    protected void setUpDataSet() {
        mDataSet.add(null);
        mDataSet.addAll(TimelineCreator.createListTimeline());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        setUpRecyclerView();
        setUpRecyclerViewOnScroll();
        setUpSwipeRefresh();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimelineFragmentListener) {
            mListener = (TimelineFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setUpRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new TimelineAdapter(mDataSet, this.getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void setUpRecyclerViewOnScroll() {
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoading && mLayoutManager.getItemCount() == mLayoutManager.findLastVisibleItemPosition() + 1) {
                    mIsLoading = true;
                    mDataSet.add(null);
                    mAdapter.notifyItemInserted(mDataSet.size() - 1);
                    new Handler().postDelayed(() -> {
                        mDataSet.remove(mDataSet.size() - 1);
                        mAdapter.notifyItemRemoved(mDataSet.size());
                        mDataSet.addAll(TimelineCreator.createListTimeline());
                        mAdapter.notifyDataSetChanged();
                        mIsLoading = false;
                    }, TIME_DELAY);
                }
            }
        });
    }

    protected void setUpSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            new Handler().postDelayed(() -> {
                mDataSet.clear();
                mDataSet.addAll(TimelineCreator.createListTimeline());
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }, TIME_DELAY);
        });
    }

    @Override
    public void onHeartImageClick(int position) {
        Timeline timeline = mDataSet.get(position);
        timeline.changeLoveState();
        mAdapter.notifyItemChanged(position);
        if (mListener != null) {
            mListener.onHeartImageClick(this.getClass(),position);
        }
    }

    public interface TimelineFragmentListener {
        void onHeartImageClick(Class fragmentClass,int position);
    }
}
