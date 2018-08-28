package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import asiantech.internship.summer.R;

public class ChartCustomView extends View {
    private Paint mPaint;
    private TextPaint mTextPaint;
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mColumnNumber;
    private int mRectNumber;
    private int mSizeNumber;
    private int mColumnWidth;
    private int mStartYear;
    private int mPaddingRectYear;
    // specify the mMode
    private int mMode;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    //two variable keep track X va Y
    private float mStartX = 0f;
    private float mStartY = 0f;
    //two variable keep track of amout we need to tr
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    //translated X and Y coordinates, cho lan tiep theo
    private float peviousTranslateX = 0f;
    private float peviousTranslateY = 0f;
    private float mTotalWidth;
    private boolean mDragged;
    private int mSpaceTwoColumn;
    private int mDifferenceOfSuccessive;
    private int mTopDefault;

    public ChartCustomView(Context context) {
        this(context, null);
    }

    public ChartCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        attributeSet(attrs);
        initPaint();
    }

    private void attributeSet(AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.WildlifeChart);
        try {
            mColumnWidth = typedArray.getInteger(R.styleable.WildlifeChart_columnSize, 70);
            mStartYear = typedArray.getInteger(R.styleable.WildlifeChart_year, 2017);
            mPaddingRectYear = typedArray.getInteger(R.styleable.WildlifeChart_paddingRectYear, 200);
            mSpaceTwoColumn = typedArray.getInteger(R.styleable.WildlifeChart_spaceTwoColumn, 45);
            mColumnNumber = typedArray.getInteger(R.styleable.WildlifeChart_columnNumber, 9);
            mSizeNumber = typedArray.getInteger(R.styleable.WildlifeChart_sizeNumber, 8);
            mRectNumber = typedArray.getInteger(R.styleable.WildlifeChart_rectNumber, 20);
        } finally {
            typedArray.recycle();
        }
    }

    private void initPaint() {
        //mpaint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(R.dimen.font_stroke_width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //text paint
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.font_text_large));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        mDifferenceOfSuccessive = getHeight() / 17;
        mTopDefault = getHeight() - getHeight() / 4 - 8 * mDifferenceOfSuccessive;
        mTotalWidth = getWidth() / 35 + mPaddingRectYear + 3 * mColumnWidth / 2 + 20 * (mPaddingRectYear + 3 * mSpaceTwoColumn) + mSpaceTwoColumn;
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());
        canvas.save();
        drawLines(canvas);
        translate(canvas);
        // draw rects and year
        drawRect(canvas);
        canvas.restore();
        //draw rect to contain datasize
        drawSize(canvas);
        //note + caption
        drawNote(canvas);
        canvas.restore();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - peviousTranslateX;
                mStartY = event.getY() - peviousTranslateY;
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                double distance = Math.abs(event.getX() - (mStartX + peviousTranslateX));
                if (distance > 0 && (mMode == DRAG)) {
                    mDragged = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                break;
            case MotionEvent.ACTION_UP:
                mMode = NONE;
                mDragged = false;
                peviousTranslateX = mTranslateX;
                peviousTranslateY = mTranslateY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG;
                peviousTranslateX = mTranslateX;
                peviousTranslateY = mTranslateY;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        if (mMode == ZOOM) {
            mScaleDetector.onTouchEvent(event);
        }
        if ((mMode == DRAG && mScaleFactor != 1f && mDragged) || mMode == ZOOM || mDragged) {
            invalidate();
        }
        return true;
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

    private void drawLines(Canvas canvas) {
        mPaint.setStrokeWidth(2);
        canvas.drawLine(getWidth() / 100, getHeight() - getHeight() / 18, getWidth(), getHeight() - getHeight() / 18, mPaint);
        mPaint.setTextSize(getResources().getDimension(R.dimen.font_text_large));
        for (int j = 1; j < mColumnNumber; j++) {
            canvas.drawLine(getWidth() / 7, getHeight() - getHeight() / 4 - j * mDifferenceOfSuccessive, getWidth() - getWidth() * 2 / 17, getHeight() - getHeight() / 4 - j * mDifferenceOfSuccessive, mPaint);
        }
        canvas.drawLine(getWidth() / 100, getHeight() - getHeight() / 4 - 11 * mDifferenceOfSuccessive, getWidth(), getHeight() - getHeight() / 4 - 11 * mDifferenceOfSuccessive, mPaint);
    }

    private void drawRect(Canvas canvas) {
        float top = getHeight() - getHeight() / 4 - 8 * getHeight() / 17;
        for (int i = 0; i <= mRectNumber; i++) {
            mPaint.setColor(getResources().getColor(R.color.colorWhales));
            canvas.drawRect(mPaddingRectYear + i * (mColumnWidth + 3 * mSpaceTwoColumn), top - (i / 8 * mDifferenceOfSuccessive) + (i / 2) * mDifferenceOfSuccessive,
                    mPaddingRectYear + mColumnWidth + i * (mColumnWidth + 3 * mSpaceTwoColumn), top + 8 * mDifferenceOfSuccessive, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawRect(mPaddingRectYear + mColumnWidth / 2 + i * (mColumnWidth + 3 * mSpaceTwoColumn) + mSpaceTwoColumn, (top - i / 8 * mDifferenceOfSuccessive) + (i / 3) * mDifferenceOfSuccessive,
                    mPaddingRectYear + mColumnWidth / 2 + i * (mColumnWidth + 3 * mSpaceTwoColumn) + mColumnWidth + mSpaceTwoColumn, top + 8 * mDifferenceOfSuccessive, mPaint);
            canvas.drawLine(getWidth() / 7, getHeight() - getHeight() / 4, getWidth(), getHeight() - getHeight() / 4, mPaint);
            //draw year
            mTextPaint.setColor(Color.BLACK);
            mTextPaint.setTextSize(getResources().getDimension(R.dimen.font_text_large));
            canvas.drawText(mStartYear + i + "", mPaddingRectYear + i * (mColumnWidth + 3 * mSpaceTwoColumn) + 20, top + 9 * mDifferenceOfSuccessive, mTextPaint);
        }
    }

    private void drawNote(Canvas canvas) {
        float positionCaptionX = getWidth() / 2 - getWidth() / 5;
        float positionCaptionY = getHeight() - getHeight() / 4 - 9 * mDifferenceOfSuccessive;
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.font_text_large));
        canvas.drawText(getResources().getString(R.string.caption), positionCaptionX, positionCaptionY, mTextPaint);
        mPaint.setColor(getResources().getColor(R.color.colorWhales));
        canvas.drawRect(getWidth() / 2 - 200, mTopDefault + 10 * mDifferenceOfSuccessive - 50, getWidth() / 2 - 150, mTopDefault + 10 * mDifferenceOfSuccessive, mPaint);
        canvas.drawText(getResources().getString(R.string.note_dolphin), getWidth() / 2 - 130, mTopDefault + 10 * mDifferenceOfSuccessive, mTextPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(getWidth() / 2 + 100, mTopDefault + 10 * mDifferenceOfSuccessive - 50, getWidth() / 2 + 150, mTopDefault + 10 * mDifferenceOfSuccessive, mPaint);
        canvas.drawText(getResources().getString(R.string.note_whales), getWidth() / 2 + 170, mTopDefault + 10 * mDifferenceOfSuccessive, mTextPaint);
    }

    private void drawSize(Canvas canvas) {
        float positionSizeY = getHeight() - getHeight() / 4 - 8 * mDifferenceOfSuccessive;
        float positionSizeX = getHeight() / 35;
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, mTopDefault - 10 * mDifferenceOfSuccessive, 3 * positionSizeX, getHeight(), mPaint);
        mPaint.setColor(Color.BLACK);
        for (int k = 0; k <= mSizeNumber; k++) {
            canvas.drawText(160 - k * 20 + "", positionSizeX, positionSizeY + k * mDifferenceOfSuccessive, mPaint);
        }
    }

    private void translate(Canvas canvas) {
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        }
        if (mTotalWidth - getWidth() + 3 * getWidth() / 35 <= -mTranslateX) {
            mTranslateX = -mTotalWidth + getWidth() - 3 * getWidth() / 35;
        }
        canvas.translate(mTranslateX / mScaleFactor, 0);
    }
}
