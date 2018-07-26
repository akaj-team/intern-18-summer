package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.canvas.models.Wildlife;

public class ChartView extends View {
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;

    private static final String CHART_NAME = "Wildlife Population";
    private static final String DOLPHINS = "dolphins";
    private static final String WHALES = "whales";
    private static final String COLOR_BLUE_LIGHT = "#1e88e5";
    private static final String COLOR_ORANGE = "#ff7043";

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreviousTranslateX = 0f;
    private float mPreviousTranslateY = 0f;

    private List<Wildlife> mListData;
    private boolean mDragged = false;

    private Paint mPaint;
    private Paint mPointPaint;
    private Paint mColumnPaint;
    private Rect mBounds;

    private ScaleGestureDetector mDetector;
    private float mScaleFactor = 1.f;

    public ChartView(Context context) {
        super(context);
        mListData = new ArrayList<>();
        createData();
        mBounds = new Rect();
        mDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListData = new ArrayList<>();
        createData();
        mBounds = new Rect();
        mDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - mPreviousTranslateX;
                mStartY = event.getY() - mPreviousTranslateY;
                break;

            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                double distance = Math.sqrt(Math.pow(event.getX() - (mStartX + mPreviousTranslateX), 2) +
                        Math.pow(event.getY() - (mStartY + mPreviousTranslateY), 2));
                if (distance > 0 && mMode == DRAG) {
                    mDragged = true;
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                break;

            case MotionEvent.ACTION_UP:
                mMode = NONE;
                mDragged = false;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;
        }

        if (mMode == ZOOM) {
            mDetector.onTouchEvent(event);
        }

        if ((mMode == DRAG && mScaleFactor != 1f) || mDragged) {
            invalidate();
        }

        return true;
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(20);

        mColumnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColumnPaint.setStyle(Paint.Style.FILL);
        mColumnPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());

        formatChart(width, height);
        drawChartLine(canvas, width, height);

        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);

        drawChartColumn(canvas, width, height);
        canvas.restore();

        setChartTitle(canvas, width, height);
        setChartNote(canvas, width, height);
        setChartNumber(canvas, width, height);
        canvas.restore();
    }

    private void formatChart(int width, int height) {
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        } else if ((mTranslateX * -1) > (mScaleFactor - 1) * width) {
            mTranslateY = (1 - mScaleFactor) * width;
        }

        if (mTranslateY * -1 < 0) {
            mTranslateY = 0;
        } else if ((mTranslateY * -1) > (mScaleFactor - 1) * height) {
            mTranslateY = (1 - mScaleFactor) * height;
        }
    }

    private void drawChartLine(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, height / 4, width, 3 * height / 4, mPaint);
        for (int i = 0; i < 9; i++) {
            mPaint.setTextSize(20);
            canvas.drawLine(width / 8, height - (height / 4 + height / 12) - i * height / 24, width - width / 32, height - (height / 4 + height / 12) - i * height / 24, mPaint);
        }
    }

    private void drawChartColumn(Canvas canvas, int width, int height) {
        int smallDistance = 10;
        int largeDistance;
        int startPosition = width / 8;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);
        for (int i = 0; i < mListData.size(); i++) {
            largeDistance = (i == 0) ? 20 : 45;
            mColumnPaint.setColor(Color.parseColor(COLOR_BLUE_LIGHT));
            canvas.drawRect(startPosition + largeDistance, height - (height / 4 + height / 12) - ((mListData.get(i).getDolphins() * height / 24) / 20), startPosition + largeDistance + 25, height - (height / 4 + height / 12), mColumnPaint);
            mColumnPaint.setColor(Color.parseColor(COLOR_ORANGE));
            canvas.drawRect(startPosition + largeDistance + 25 + smallDistance, height - (height / 4 + height / 12) - ((mListData.get(i).getWhales() * height / 24) / 20), startPosition + largeDistance + 25 + smallDistance + 25, height - (height / 4 + height / 12), mColumnPaint);

            canvas.drawText(String.valueOf(mListData.get(i).getYear()), startPosition + largeDistance + 10, height - (height / 4 + height / 12) + 20, mPaint);

            startPosition += largeDistance + smallDistance + 50;
        }
    }

    private void setChartTitle(Canvas canvas, int width, int height) {
        mPaint.setTextSize(40);
        mPaint.getTextBounds(CHART_NAME, 0, CHART_NAME.length(), mBounds);
        canvas.drawText(CHART_NAME, width / 2.0f - (mBounds.width() / 2), height / 4 + height / 16, mPaint);
    }

    private void setChartNote(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.parseColor(COLOR_BLUE_LIGHT));
        canvas.drawPoint(width / 2 - width / 6, height - (height / 4 + height / 32), mPointPaint);
        mPaint.setTextSize(25);
        mPaint.getTextBounds(DOLPHINS, 0, DOLPHINS.length(), mBounds);
        canvas.drawText(DOLPHINS, width / 2 - width / 6 + 30, height - (height / 4 + height / 32 - 10), mPaint);

        mPointPaint.setColor(Color.parseColor(COLOR_ORANGE));
        canvas.drawPoint(width / 2 + width / 12, height - (height / 4 + height / 32), mPointPaint);
        mPaint.setTextSize(25);
        mPaint.getTextBounds(WHALES, 0, WHALES.length(), mBounds);
        canvas.drawText(WHALES, width / 2 + width / 12 + 30, height - (height / 4 + height / 32 - 10), mPaint);
    }

    private void setChartNumber(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, height / 4 + 1, width / 8, 3 * height / 4 - 1, mPaint);

        mPaint.setTextSize(20);
        mPaint.setColor(Color.BLACK);
        for (int i = 0; i < 9; i++) {
            canvas.drawText(String.valueOf(i * 20), width / 16, height - (height / 4 + height / 12) - i * height / 24, mPaint);
        }
    }

    private void createData() {
        mListData.add(new Wildlife(2017, 150, 80));
        mListData.add(new Wildlife(2018, 138, 90));
        mListData.add(new Wildlife(2019, 125, 128));
        mListData.add(new Wildlife(2020, 58, 98));
        mListData.add(new Wildlife(2021, 95, 33));
        mListData.add(new Wildlife(2022, 113, 57));
        mListData.add(new Wildlife(2023, 14, 140));
        mListData.add(new Wildlife(2024, 32, 135));
        mListData.add(new Wildlife(2025, 115, 37));
        mListData.add(new Wildlife(2026, 77, 160));
        mListData.add(new Wildlife(2027, 48, 66));
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            invalidate();
            return true;
        }
    }
}
