package asiantech.internship.summer.view_pager.timeline_view_pager;

import asiantech.internship.summer.recycler_view.TimeLineFragment;
import asiantech.internship.summer.recycler_view.model.Timeline;
import asiantech.internship.summer.recycler_view.model.TimelineCreator;

public class MainTimelineFragment extends TimeLineFragment {

    @Override
    protected void setUpDataSet() {
        mDataSet.addAll(TimelineCreator.createListTimeline());
    }

    public Timeline getTimelineAt(int position) {
        return mDataSet.get(position);
    }

    public void changeLovedStatus(Timeline timeline) {
        int position = mDataSet.indexOf(timeline);
        if (position >= 0) {
            mAdapter.notifyItemChanged(position);
        }
    }
}
