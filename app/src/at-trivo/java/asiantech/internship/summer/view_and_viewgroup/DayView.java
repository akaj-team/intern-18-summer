package asiantech.internship.summer.view_and_viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class DayView extends RelativeLayout {

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View rootView = inflate(context, R.layout.custome_day, this);
        TextView tvDay = rootView.findViewById(R.id.tvDay);
        TextView tvDayNumber = rootView.findViewById(R.id.tvDayNumber);
        RelativeLayout rlDayNumber = rootView.findViewById(R.id.rlDayNumber);
        View viewHasMessage = rootView.findViewById(R.id.viewHasMessage);

        final TypedArray dayCustomViewTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DayView);

        try {
            String dayInput = dayCustomViewTypedArray.getString(R.styleable.DayView_day);
            String dayNumberInput = dayCustomViewTypedArray.getString(R.styleable.DayView_dayNumber);
            boolean isToday = dayCustomViewTypedArray.getBoolean(R.styleable.DayView_isToday, false);
            boolean isHasEvent = dayCustomViewTypedArray.getBoolean(R.styleable.DayView_isHasEvent, false);

            tvDay.setText(dayInput);
            tvDayNumber.setText(dayNumberInput);
            if (isToday) {
                rlDayNumber.setBackgroundResource(R.drawable.bg_circle_day);
            }
            if (isHasEvent) {
                viewHasMessage.setBackgroundResource(R.drawable.bg_circle_has_message);
            }
        } finally {
            dayCustomViewTypedArray.recycle();
        }
    }
}
