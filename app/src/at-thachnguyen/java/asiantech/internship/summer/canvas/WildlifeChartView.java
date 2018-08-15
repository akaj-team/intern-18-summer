package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.canvas.model.Wildlife;

public class WildlifeChartView extends View {
    private final ScaleGestureDetector mDetector;
    private Paint mPaint;
    private List<Wildlife> mWildlifeList = new ArrayList<>();
    private float mScaleFactor = 1.f;
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
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
    private boolean mDragged = false;
    private float mTotalWidth = 0;

    public WildlifeChartView(Context context) {
        super(context);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public WildlifeChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public WildlifeChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());
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
                double distance = Math.abs(event.getX() - (mStartX + mPreviousTranslateX));
                if (distance > 0) {
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
        if ((mMode == DRAG && mScaleFactor != 1f && mDragged) || mMode == ZOOM || mDragged) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        }
        if (-mTranslateX >= mTotalWidth - getWidth() + 80) {
            mTranslateX = -(mTotalWidth - getWidth() + 80);
        }
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        mPaint.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_title));
        mPaint.setStyle(Paint.Style.FILL);
        float top = getHeight() * 3 / 4.0f;
        float unit=(getHeight()-2*(getHeight()-top)-40)/maxChartValue(mWildlifeList);
        int exponent= (int) Math.log10(maxChartValue(mWildlifeList)/13);
        int distance= (int) ((int) ((maxChartValue(mWildlifeList)/13)%Math.pow(10, exponent))*Math.pow(10, exponent));
        float space = distance * unit;
        canvas.drawText(getResources().getString(R.string.title_chart), (getWidth() - 400) / 2.0f, top - 80 - unit * maxChartValue(mWildlifeList), mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_name));
        canvas.drawText(getResources().getString(R.string.dolphins), 60, top + 70, mPaint);
        canvas.drawText(getResources().getString(R.string.whales), 200, top + 70, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorSalemapprox));
        canvas.drawRect(40, top + 55, 55, top + 70, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorTango));
        canvas.drawRect(180, top + 55, 195, top + 70, mPaint);
        canvas.translate(mTranslateX, 0);
        drawChart(canvas, top, space, distance, unit);
        canvas.restore();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getWidth() - 5, top - 140 - unit * maxChartValue(mWildlifeList), getWidth(), top + 50, mPaint);
        canvas.drawRect(0, top - 140 - unit * maxChartValue(mWildlifeList), 80, top + 50, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(5, top - 140 - unit * maxChartValue(mWildlifeList), getWidth() - 5, top + 100, mPaint);
        drawLine(canvas, top, space, distance);
    }

    @SuppressWarnings("deprecation")
    private void initPaint() {
        createWildlifeList();
        WindowManager w = ((Activity) getContext()).getWindowManager();
        float element = (w.getDefaultDisplay().getWidth() - 110) / (13.0f * 6);
        mTotalWidth = 80 + 7 * element * mWildlifeList.size() + 6 * element * (mWildlifeList.size() - 1);
        setFadingEdgeLength(0);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        mPaint.setStrokeWidth(1);
    }

    private void createWildlifeList() {
        mWildlifeList.add(new Wildlife(40, 25, 2017));
        mWildlifeList.add(new Wildlife(68, 56, 2018));
        mWildlifeList.add(new Wildlife(70, 90, 2019));
        mWildlifeList.add(new Wildlife(88, 90, 2020));
        mWildlifeList.add(new Wildlife(80, 160, 2021));
        mWildlifeList.add(new Wildlife(98, 90, 2022));
        mWildlifeList.add(new Wildlife(78, 9, 2023));
        mWildlifeList.add(new Wildlife(80, 166, 2024));
        mWildlifeList.add(new Wildlife(70, 90, 2025));
        mWildlifeList.add(new Wildlife(40, 25, 2026));
        mWildlifeList.add(new Wildlife(80, 160, 2027));
        mWildlifeList.add(new Wildlife(70, 90, 2028));
        mWildlifeList.add(new Wildlife(98, 90, 2029));
        mWildlifeList.add(new Wildlife(78, 9, 2030));
        mWildlifeList.add(new Wildlife(80, 160, 2031));
        mWildlifeList.add(new Wildlife(70, 90, 2032));
        mWildlifeList.add(new Wildlife(300, 350, 2033));
    }

    private float maxChartValue(List<Wildlife> wildlifeList) {
        List<Float> listChartValue = new ArrayList<>();
        for (Wildlife wildlife : wildlifeList) {
            listChartValue.add(wildlife.getDolphin());
            listChartValue.add(wildlife.getWhale());
        }
        return Collections.max(listChartValue);
    }

    private void drawLine(Canvas canvas, float top, float space, int distance) {
        int n = 0;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        while (n <= maxChartValue(mWildlifeList) + distance) {
            canvas.drawText(String.valueOf(n), 40, top, mPaint);
            top -= space;
            n += distance;
        }
    }

    private void drawChart(Canvas canvas, float top, float space, int distance, float unit) {
        float element = (getWidth() - 110) / (13.0f * 6);
        float left = 80 + element * 3;
        int n = 0;
        mPaint.setStyle(Paint.Style.FILL);
        float totalWidth = left + 7 * element * mWildlifeList.size() + 6 * element * (mWildlifeList.size() - 1);
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        float line=top;
        if (totalWidth + 30 > getWidth()) {
            while (n <= maxChartValue(mWildlifeList) + distance) {
                canvas.drawLine(80, line, totalWidth, line , mPaint);
                line-=space;
                n += distance;
            }
        } else {
            while (n <= maxChartValue(mWildlifeList) + distance) {
                canvas.drawLine(80, line , getWidth() - 30 - 3 * element, line, mPaint);
                line-=space;
                n += distance;
            }
        }
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        mPaint.setStyle(Paint.Style.FILL);
        for (Wildlife wildlife : mWildlifeList) {
            mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
            mPaint.setTextSize(20);
            canvas.drawText(String.valueOf(wildlife.getYear()), left, top + 25, mPaint);
            mPaint.setColor(getResources().getColor(R.color.colorSalemapprox));
            canvas.drawRect(left, top, left += element * 3, top - wildlife.getDolphin() * unit, mPaint);
            mPaint.setColor(getResources().getColor(R.color.colorTango));
            canvas.drawRect(left += element, top, left += element * 3, top - wildlife.getWhale() * unit, mPaint);
            left += element * 6;
        }
        mPaint.setColor(getResources().getColor(R.color.colorDoveGray));
        mPaint.setStyle(Paint.Style.FILL);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            if (mScaleFactor > 1f) {
                invalidate();
            }
            return true;
        }
    }
}
