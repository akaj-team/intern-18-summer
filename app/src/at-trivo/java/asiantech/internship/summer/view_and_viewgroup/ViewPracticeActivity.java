package asiantech.internship.summer.view_and_viewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import asiantech.internship.summer.R;

public class ViewPracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pracice);
    }

    public void customSelector(View view) {
        int[] eventViewIds = new int[]{R.id.eventView1, R.id.eventView2, R.id.eventView3};
        for (int eventId : eventViewIds) {
            EventView eventView = findViewById(eventId);
            eventView.setClickColor(eventId == view.getId());
        }
    }
}
