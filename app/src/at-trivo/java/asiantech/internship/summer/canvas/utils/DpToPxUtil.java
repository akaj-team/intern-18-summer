package asiantech.internship.summer.canvas.utils;

import android.content.Context;
import android.util.TypedValue;

public final class DpToPxUtil {
    private DpToPxUtil(){
        // no-op
    }

    public static float convertDpToPixels(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
