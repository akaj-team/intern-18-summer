package asiantech.internship.summer.view_and_viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class EventView extends RelativeLayout {

    public EventView(Context context) {
        this(context, null);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View rootView = inflate(context, R.layout.custome_event, this);
        TextView tvMessage = rootView.findViewById(R.id.tvMessage);
        CircleImageView imgProfile = rootView.findViewById(R.id.imgProfile);
        TextView tvTime = rootView.findViewById(R.id.tvTime);
        TextView tvLocation = rootView.findViewById(R.id.tvLocation);
        final TypedArray evenCustomViewTypedArray = context.obtainStyledAttributes(attrs, R.styleable.EventView);
        try {
            String imageLink = evenCustomViewTypedArray.getString(R.styleable.EventView_image_src);
            String messageInput = evenCustomViewTypedArray.getString(R.styleable.EventView_message);
            String timeInput = evenCustomViewTypedArray.getString(R.styleable.EventView_time);
            String locationInput = evenCustomViewTypedArray.getString(R.styleable.EventView_location);

            Glide.with(this)
                    .load(imageLink)
                    .apply(new RequestOptions().override(100, 100).centerCrop())
                    .into(imgProfile);
            tvMessage.setText(messageInput);
            tvTime.setText(timeInput);
            tvLocation.setText(locationInput);
        } finally {
            evenCustomViewTypedArray.recycle();
        }
    }

    public void setClickColor(boolean isClick) {
        View viewStatus = this.findViewById(R.id.viewStatus);
        int colorIndex = isClick ? R.color.colorBrightTurquoise : R.color.colorWhite;
        viewStatus.setBackgroundColor(getResources().getColor(colorIndex));
    }
}
