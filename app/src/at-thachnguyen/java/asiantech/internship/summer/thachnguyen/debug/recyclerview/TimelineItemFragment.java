package asiantech.internship.summer.thachnguyen.debug.recyclerview;

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
import asiantech.internship.summer.thachnguyen.debug.recyclerview.model.TimelineItem;

import static asiantech.internship.summer.R.layout.fragment_timeline_item;


public class TimelineItemFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragment_timeline_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<TimelineItem> mListTimelines = TimelineItem.creatListTimeline();
        RecyclerView mRecyclerViewTimeline = view.findViewById(R.id.recyclerViewTimeline);
        TimelineAdapter timelineAdapter=new TimelineAdapter(getContext(), mListTimelines);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerViewTimeline.setAdapter(timelineAdapter);
        mRecyclerViewTimeline.setLayoutManager(layoutManager);
    }
}
