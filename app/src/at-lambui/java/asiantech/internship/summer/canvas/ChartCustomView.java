package asiantech.internship.summer.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class ChartCustomView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    TextPaint mTextPaintMeasure = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;
    private float scaleFactor = 1.f;
    private ScaleGestureDetector detector;
    // specify the mode
    private static int NONE = 0;
    private static int DRAG = 1;
    private static int ZOOM = 2;
    private int mode;
    //two variable keep track X va Y
    private float startX = 0f;
    private float startY = 0f;
    //two variable keep track of amout we need to tr

    private float translateX = 0f;
    private float translateY = 0f;

    //translated X and Y coordinates, cho lan tiep theo
    private float peviousTranslateX = 0f;
    private float peviousTranslateY = 0f;


    // ADD new scroll
    private static final int INVALID_POINTER_ID = -1;

    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;
    private int mTotalWidth;

    double distance;
    boolean dragged;

    public ChartCustomView(Context context) {
        this(context, null);
    }

    public ChartCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());
        initPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                /*ADD new*/
                final float x = event.getX();
                final float y = event.getY();
                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = event.getPointerId(0);
                /**/
                mode = DRAG;

                // assign current X,Y ,finger to startX and startY minus
                //
                //gia tri cho no o day la 0;
                startX = event.getX() - peviousTranslateX;
                startY = event.getY() - peviousTranslateY;
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                translateX = event.getX() - startX;
                translateY = event.getY() - startY;
                /**/
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                if (!detector.isInProgress()) {
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;
                    mPosX += dx;
                    mPosY += dy;
                    invalidate();
                }
                mLastTouchX = x;
                mLastTouchY = y;

                /* can get actual coordinates of the finger*/
//            double distance = Math.sqrt(Math.pow(event.getX() - (startX + peviousTranslateX), 2)) +
//                    Math.pow(event.getY() - (startY + peviousTranslateY), 2)

                distance = Math.sqrt(Math.pow(event.getX() - (startX + peviousTranslateX), 2) +

                        Math.pow(event.getY() - (startY + peviousTranslateY), 2));
                if (distance > 0 && (mode == DRAG)) {

                    dragged = true;

                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                mode = ZOOM;
                break;
            }
            case MotionEvent.ACTION_UP: {
                mode = NONE;
                dragged = false;
                //finger went up, translateX and Y int periousTranslate and
                //periousTranslateY
                peviousTranslateX = translateX;
                peviousTranslateY = translateY;
                mActivePointerId = INVALID_POINTER_ID;

                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                mode = DRAG;
                peviousTranslateX = translateX;
                peviousTranslateY = translateY;

                final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
        }
        if (mode == ZOOM) {
            detector.onTouchEvent(event);
        }

        if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM) {
            invalidate();
        }
        return true;

    }

    private void initPaint() {
        //mpaint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //text paint
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#FF000000"));
        mTextPaint.setTextSize(50);
        mTextPaintMeasure = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintMeasure.setStrokeWidth(3);

        //


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();


        float startX = 0;
        float startY = 0;
        float d = getHeight() / 17;
        float width = getWidth();
        float height = getHeight();


        float Xwidth = width / 35;
        canvas.save();

        canvas.scale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
//        canvas.restore();

        if ((translateX * -1) < 0) {
            translateX = 0;
        } else if ((translateX * -1) > (scaleFactor - 1) * getHeight()) {
            translateY = (1 - scaleFactor) * getHeight();
        }
        if (translateY * -1 < 0) {
            translateY = 0;
        } else if ((translateY * -1) > (scaleFactor - 1) * getHeight()) {
            translateY = (1 + scaleFactor) * getHeight();
        }
        canvas.translate(translateX / scaleFactor, translateY / scaleFactor);
        //create


        Log.e("TTT", "rong" + getWidth() + " dai " + getHeight());
        //canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        //canvas.drawLine(10, 20, 30, 40, mPaint);

        //straight line
        mPaint.setStrokeWidth(2);
        canvas.drawLine(width / 100, getHeight() - height / 18, getWidth(), getHeight() - height / 18, mPaint);

        mPaint.setTextSize(50);
        for (int j = 1; j < 9; j++) {
            canvas.drawLine(width / 7, getHeight() - height / 4 - j * d, getWidth() - width * 2 / 17, getHeight() - height / 4 - j * d, mPaint);
        }

        canvas.drawLine(width / 100, getHeight() - height / 4 - 11 * d, getWidth(), getHeight() - height / 4 - 11 * d, mPaint);
        //150= wid/7
        canvas.save();
        //neu so luong cot nho hon getwight thi cho luot

//        if ((mPosX < 0) && (xxx < canvas.getWidth()))
//            mPosX = 0;
//        if ((mPosY < 0) && (xxx < canvas.getHeight()))

//            mPosY = 0;


        mTotalWidth = Xwidth +
                canvas.translate(mPosX, mPosY);


        //ve diem
        //ve line

        //canvas.drawLine(, 20, 90, 20, mPaint);

        //draw text
//        mTextPaint.setStrokeWidth(3);
//        mTextPaint.setTextSize(50);
        float a = getWidth() / 2 - width / 5;
        float b = getHeight() - height / 4 - 9 * d;
        mTextPaintMeasure.setStrokeWidth(3);
        mTextPaintMeasure.setTextSize(50);

        float Yheight = getHeight() - height / 4 - 8 * d;

        //draw measure
//        List<Integer> listDolphins = new ArrayList<>();
        //draw rect
        float widthRect1 = 70;
        float X_Space = 45;
        float heightRect1 = 200;
        float left = 200;
        float top = Yheight;

        /*paint react*/
        for (int i = 0; i <= 20; i++) {
            //
            mPaint.setColor(Color.CYAN);
            canvas.drawRect(left + i * (widthRect1 + 3 * X_Space), top - (i / 8 * d) + (i / 2) * d, left + widthRect1 + i * (widthRect1 + 3 * X_Space), top + 8 * d, mPaint);

            mPaint.setColor(Color.BLUE);
            canvas.drawRect(left + widthRect1 / 2 + i * (widthRect1 + 3 * X_Space) + X_Space, (top - i / 8 * d) + (i / 3) * d, left + widthRect1 / 2 + i * (widthRect1 + 3 * X_Space) + widthRect1 + X_Space, top + 8 * d, mPaint);

//            canvas.drawLine(width / 7, getHeight() - height / 4  , getWidth() - width * 2 / 17, getHeight() - height / 4 , mPaint);
            canvas.drawLine(width / 7, getHeight() - height / 4, getWidth(), getHeight() - height / 4, mPaint);
            mTextPaint.setColor(Color.BLACK);
            canvas.drawText(2017 + i + "", left + i * (widthRect1 + 3 * X_Space) + 20, top + 9 * d, mTextPaint);

        }

        canvas.restore();
        //trans
        canvas.drawText("Wildlife Population", a, b, mTextPaint);

        mPaint.setColor(Color.WHITE);

        canvas.drawRect(0, getHeight() - height / 8, Xwidth, getHeight() - height / 8, mPaint);
        mPaint.setColor(Color.BLACK);

        for (int k = 0; k <= 8; k++) {
            canvas.drawText(160 - k * 20 + "", Xwidth, Yheight + k * d, mPaint);

        }
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(getWidth() / 2 - 200, top + 10 * d - 50, getWidth() / 2 - 150, top + 10 * d, mPaint);
        canvas.drawText("Dophins", getWidth() / 2 - 130, top + 10 * d, mTextPaint);
        canvas.drawRect(getWidth() / 2 + 100, top + 10 * d - 50, getWidth() / 2 + 150, top + 10 * d, mPaint);
        canvas.drawText("Whales", getWidth() / 2 + 170, top + 10 * d, mTextPaint);
        //add note
        /*
         *
         *
         *
         * */
        canvas.restore();

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            invalidate();
            return true;
        }
    }
}
