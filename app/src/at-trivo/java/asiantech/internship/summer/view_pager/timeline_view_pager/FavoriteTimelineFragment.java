package asiantech.internship.summer.view_pager.timeline_view_pager;

import asiantech.internship.summer.recycler_view.TimeLineFragment;
import asiantech.internship.summer.recycler_view.model.Timeline;

public class FavoriteTimelineFragment extends TimeLineFragment {

    @Override
    protected void setUpDataSet() {
        //set no data
    }

    @Override
    protected void setUpRecyclerViewOnScroll() {
        //set no scroll action
    }

    @Override
    protected void setUpSwipeRefresh() {
        //set no swipe refresh action
        mSwipeRefreshLayout.setEnabled(false);
    }

    public Timeline removeTimelineAt(int position) {
        Timeline timeline = mDataSet.remove(position);
        mAdapter.notifyItemRemoved(position);
        return timeline;
    }

    public void addTimeline(Timeline timeline) {
        mDataSet.add(0, timeline);
        mAdapter.notifyDataSetChanged();
    }

    public void removeTimeline(Timeline timeline) {
        int position = mDataSet.indexOf(timeline);
        if (position >= 0) {
            mDataSet.remove(timeline);
            mAdapter.notifyItemRemoved(position);
        }
    }
}
