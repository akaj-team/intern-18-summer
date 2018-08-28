package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

import asiantech.internship.summer.activity_fragment.ActivityFragmentActivity;
import asiantech.internship.summer.asynctack_thread_handler.AsyncTaskThreadHandlerActivity;
import asiantech.internship.summer.canvas.CanvasActivity;
import asiantech.internship.summer.recycler_view.RecyclerViewActivity;
import asiantech.internship.summer.restful.RestfulActivity;
import asiantech.internship.summer.unittest.UnitTestActivity;
import asiantech.internship.summer.view_and_viewgroup.ViewPracticeActivity;
import asiantech.internship.summer.view_pager.ViewPagerActivity;

public class MenuActivity extends AppCompatActivity {

    private HashMap<String, Class> mMap;
    private Button mBttOpenActivity;
    private Spinner mSpnTitle;
    private String[] mMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mBttOpenActivity = findViewById(R.id.bttOpenActivity);
        mMenuList = getResources().getStringArray(R.array.homework_title);
        mSpnTitle = findViewById(R.id.spnTitle);
        setUpButton();
        setUpTitleSpinner();
        setUpMap();
    }

    private void setUpButton() {
        mBttOpenActivity.setOnClickListener(view -> {
            String title = mSpnTitle.getSelectedItem().toString();
            Intent intent = new Intent(MenuActivity.this, mMap.get(title));
            startActivity(intent);
        });
    }

    private void setUpTitleSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mMenuList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnTitle.setAdapter(adapter);
        mSpnTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringBuilder stringBuilder = new StringBuilder().append(getResources().getString(R.string.open)).append(mMenuList[position]);
                mBttOpenActivity.setText(stringBuilder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBttOpenActivity.setText(R.string.choice_activity_label);
            }
        });
    }

    private void setUpMap() {
        mMap = new HashMap<>();
        mMap.put(mMenuList[0], ViewPracticeActivity.class);
        mMap.put(mMenuList[1], RecyclerViewActivity.class);
        mMap.put(mMenuList[2], ViewPagerActivity.class);
        mMap.put(mMenuList[3], ActivityFragmentActivity.class);
        mMap.put(mMenuList[4], RestfulActivity.class);
        mMap.put(mMenuList[5], CanvasActivity.class);
        mMap.put(mMenuList[6], AsyncTaskThreadHandlerActivity.class);
        mMap.put(mMenuList[7], UnitTestActivity.class);
    }
}
