package asiantech.internship.summer.canvas;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import asiantech.internship.summer.R;
import asiantech.internship.summer.canvas.utils.DpToPxUtil;

public class WildlifeChartView extends View {
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final int ZOOM_MODE = 1;
    private static final int DRAG_MODE = 2;
    private static final int NONE_MODE = 0;
    private static final String TAG = WildlifeChartView.class.getSimpleName();

    private int[][] mData;
    private String[] mTypes;
    private int[] mColors;
    private String mCaption;
    private int mBackgroundColor;
    private int mYear;
    private int rulerWidth;
    private int captionHeight;
    private int bottomHeight;
    private int columnSize;
    private int columnFarDistance;
    private int columnNearDistance;

    private Rect mTextRect;
    private Paint mPaint;

    private ScaleGestureDetector mDetector;
    private float mScaleFactor = 1.f;
    private float mScrollFactor = 0;
    private int mMaxScroll;
    private float mCurrentX;
    private float mCurrentY;
    private float mLastScrollValue = 0;
    private int mMode = NONE_MODE;
    private boolean mHasChange = false;
    private Handler mHandler;
    private ScrollMaker mScrollMaker;

    public WildlifeChartView(Context context) {
        this(context, null);
    }

    public WildlifeChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WildlifeChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mBackgroundColor = getContext().getResources().getColor(R.color.colorDesertStorm);
        mTextRect = new Rect();
        mPaint = new Paint();
        mHandler = new Handler();
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.WildlifeChartView, defStyle, 0);
        try {
            int dataArrayReference = typedArray.getResourceId(R.styleable.WildlifeChartView_columnData, 0);
            initColumnData(dataArrayReference);

            int colorArrayReference = typedArray.getResourceId(R.styleable.WildlifeChartView_columnColor, 0);
            initColumnColor(colorArrayReference);

            int captionReference = typedArray.getResourceId(R.styleable.WildlifeChartView_caption, 0);
            mCaption = getContext().getString(captionReference);

            mYear = typedArray.getInteger(R.styleable.WildlifeChartView_year, 2000);
            rulerWidth = typedArray.getInteger(R.styleable.WildlifeChartView_rulerWidth, 50);
            captionHeight = typedArray.getInteger(R.styleable.WildlifeChartView_captionHeight, 100);
            bottomHeight = typedArray.getInteger(R.styleable.WildlifeChartView_bottomHeight, 100);
            columnSize = typedArray.getInteger(R.styleable.WildlifeChartView_columnSize, 30);
            columnNearDistance = typedArray.getInteger(R.styleable.WildlifeChartView_columnNearDistance, 10);
            columnFarDistance = typedArray.getInteger(R.styleable.WildlifeChartView_columnFarDistance, 60);

        } finally {
            typedArray.recycle();
        }
    }

    private void initColumnData(int dataArrayReference) {
        final TypedArray dataTypedArray = getContext().getResources().obtainTypedArray(dataArrayReference);
        try {
            int length = dataTypedArray.length();
            mData = new int[length][];
            mTypes = new String[length];
            for (int i = 0; i < length; i++) {
                int dataReference = dataTypedArray.getResourceId(i, 0);
                String type = getContext().getResources().getResourceName(dataReference);
                type = type.substring(type.indexOf('/') + 1);
                mTypes[i] = type;
                int[] part = getContext().getResources().getIntArray(dataReference);
                mData[i] = part;
            }
        } finally {
            dataTypedArray.recycle();
        }
    }

    private void initColumnColor(int colorArrayReference) {
        final TypedArray colorTypedArray = getContext().getResources().obtainTypedArray(colorArrayReference);
        try {
            int length = colorTypedArray.length();
            mColors = new int[length];
            for (int i = 0; i < length; i++) {
                int colorReference = colorTypedArray.getResourceId(i, 0);
                int color = getContext().getResources().getColor(colorReference);
                mColors[i] = color;
            }
        } finally {
            colorTypedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight - rulerWidth;
        int contentHeight = getHeight() - paddingTop - paddingBottom - captionHeight - bottomHeight;
        getMaxScrollValue(contentWidth);

        int startX = paddingLeft + rulerWidth;
        int startY = contentHeight + paddingTop + captionHeight;

        int[] ruler = getRuler();
        int stepDif = ruler[0];
        int maxStepNumber = ruler[1];
        int stepHeight = contentHeight / maxStepNumber;
        float valueToHeight = stepHeight / (float) stepDif;

        drawBackground(startX, startY, contentWidth, maxStepNumber, stepHeight, canvas);

        canvas.save();
        canvas.translate(-mScrollFactor / mScaleFactor, 0);
        drawChart(startX, startY, valueToHeight, canvas);
        canvas.restore();

        drawRulerNumber(startX, startY, stepHeight, stepDif, maxStepNumber, canvas);
        drawCaption(paddingTop, paddingLeft,
                paddingTop + captionHeight, getWidth(), canvas);
        drawNode(startX, startY, canvas);
        canvas.restore();
        mHasChange = false;
    }

    private void drawBackground(int startX, int startY, int contentWidth, int maxStepNumber, int stepHeight, Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        mPaint.setColor(Color.BLACK);
        for (int i = 0; i <= maxStepNumber; i++) {
            mPaint.setStrokeWidth(1);
            canvas.drawLine(startX, startY - i * stepHeight,
                    startX + contentWidth, startY - i * stepHeight, mPaint);
        }
    }

    private void drawRulerNumber(int startX, int startY, int stepHeight, int stepDif, int maxStep, Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, captionHeight, startX, startY + rulerWidth, mPaint);

        mPaint.setColor(Color.BLACK);
        float dp = getResources().getDimension(R.dimen.text_size_medium);
        mPaint.setTextSize(DpToPxUtil.convertDpToPixels(dp, getContext()));
        mPaint.setStrokeWidth(5);
        for (int i = 0; i <= maxStep; i++) {
            String value = i * stepDif + "";
            mPaint.getTextBounds(value, 0, value.length(), mTextRect);
            canvas.drawText(value, startX / 2 - mTextRect.width() / 2,
                    startY - i * stepHeight + mTextRect.height() / 2, mPaint);
        }
    }

    private void drawCaption(int top, int left, int bottom, int right, Canvas canvas) {
        int captionBoxHeight = bottom - top;
        int captionBoxWidth = right - left;
        float dp = getResources().getDimension(R.dimen.text_size_big);
        mPaint.setTextSize(DpToPxUtil.convertDpToPixels(dp, getContext()));
        mPaint.getTextBounds(mCaption, 0, mCaption.length(), mTextRect);
        canvas.drawText(mCaption, left + captionBoxWidth / 2 - mTextRect.width() / 2 - mTextRect.left,
                top + captionBoxHeight / 2 + mTextRect.height() / 2 - mTextRect.bottom, mPaint);
    }

    private void drawChart(int startX, int startY, double valueToHeight, Canvas canvas) {
        int distanceBetweenYear = mData.length * columnSize + columnFarDistance + columnNearDistance * (mData.length - 1);
        int distanceBetweenColumnInYear = columnSize + columnNearDistance;
        int length = mData[0].length;

        for (int i = 0; i < length; i++) {
            mPaint.setColor(mColors[0]);
            int startYearX = startX + rulerWidth + distanceBetweenYear * i;
            int typeNumber = mData.length;
            for (int j = 0; j < typeNumber; j++) {
                mPaint.setColor(mColors[j]);
                canvas.drawRect(startYearX + distanceBetweenColumnInYear * j,
                        (int) (startY - valueToHeight * mData[j][i]),
                        startYearX + distanceBetweenColumnInYear * j + columnSize,
                        startY,
                        mPaint);
            }

            float dp = getResources().getDimension(R.dimen.text_size_medium);
            mPaint.setTextSize(DpToPxUtil.convertDpToPixels(dp, getContext()));
            mPaint.setColor(Color.BLACK);
            String printedYear = mYear + i * 5 + "";
            mPaint.getTextBounds(printedYear, 0, printedYear.length(), mTextRect);

            canvas.drawText(printedYear,
                    startYearX + columnSize + columnNearDistance / 2f - mTextRect.width() / 2f - mTextRect.left,
                    startY + 10 + mTextRect.height(),
                    mPaint);
        }
    }

    private void drawNode(int startX, int startY, Canvas canvas) {
        int distanceType = 40;
        int distanceColorText = 10;
        int side = 30;
        int lastSize = 0;
        startY += bottomHeight / 2;
        float dp = getResources().getDimension(R.dimen.text_size_medium);
        mPaint.setTextSize(DpToPxUtil.convertDpToPixels(dp, getContext()));
        int length = mColors.length;
        for (int i = 0; i < length; i++) {
            int startOfIndexX = startX + i * (lastSize + distanceType);
            mPaint.setColor(mColors[i]);
            canvas.drawRect(startOfIndexX, startY, startOfIndexX + side, startY + side, mPaint);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(mTypes[i], startOfIndexX + distanceColorText + side, startY + side, mPaint);
            mPaint.getTextBounds(mTypes[i], 0, mTypes[i].length(), mTextRect);
            lastSize = side + mTextRect.width() + distanceColorText;
        }
    }

    private int[] getRuler() {
        double max = 0;
        int step = 1;
        for (int[] row : mData) {
            for (int value : row) {
                if (value > max) {
                    max = value;
                }
            }
        }
        while (max > 100) {
            max /= 10;
            step *= 10;
        }
        while (max > 10) {
            max /= 2;
            step *= 2;
        }
        return new int[]{step, (int) Math.round(max)};
    }

    private void getMaxScrollValue(int contentWidth) {
        int distanceBetweenYear = mData.length * columnSize + columnFarDistance + columnNearDistance * (mData.length - 1);
        mMaxScroll = distanceBetweenYear * mData[0].length - contentWidth;
        mMaxScroll = Math.max(0, mMaxScroll);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                mMode = DRAG_MODE;
                mCurrentX = motionEvent.getX();
                mCurrentY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = motionEvent.getX() - mCurrentX;
                float dy = motionEvent.getY() - mCurrentY;
                mCurrentX = motionEvent.getX();
                mCurrentY = motionEvent.getY();
                if (mMode == DRAG_MODE && dx * dx > 4 * dy * dy) {
                    mScrollFactor -= dx;
                    mLastScrollValue = dx;
                    mScrollFactor = Math.max(0, Math.min(mScrollFactor, mMaxScroll));
                }
                mHasChange = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM_MODE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG_MODE;
                break;
            case MotionEvent.ACTION_UP:
                mMode = NONE_MODE;
                if (mScrollMaker != null) {
                    mScrollMaker.endScroll();
                }
                mScrollMaker = new ScrollMaker(mLastScrollValue);
                mScrollMaker.start();
                break;
        }
        if (mMode == ZOOM_MODE) {
            mDetector.onTouchEvent(motionEvent);
        }
        if (mHasChange) {
            invalidate();
        }
        return true;
    }

    public boolean performClick() {
        super.performClick();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            mHasChange = true;
            return true;
        }
    }

    class ScrollMaker extends Thread {
        private float scrollValue;
        private boolean running;

        ScrollMaker(float scrollValue) {
            this.scrollValue = scrollValue;
            running = true;
        }

        public void run() {
            float delta = scrollValue / 10;
            while (scrollValue * scrollValue > 1 && running) {
                mScrollFactor -= scrollValue / 2;
                mScrollFactor = Math.max(0, Math.min(mScrollFactor, mMaxScroll));
                scrollValue -= delta;
                mHandler.post(WildlifeChartView.this::invalidate);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        void endScroll() {
            running = false;
        }
    }
}
