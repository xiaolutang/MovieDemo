package com.example.xiaolutang.moviedemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.xiaolutang.moviedemo.R;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/4/15
 * description：
 */
public class ColorTrackView extends View {
    private final String TAG = getClass().getSimpleName();

    private int mTextStartX;
    private int mTextStartY;

    public enum Direction{
        LEFT, RIGTH, TOP, BOTTOM;
    }

    private final int DIRECTION_LEFT = 0;
    private final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_TOP = 2;
    private static final int DIRECTION_BOTTOM = 3;

    public void setDirection(int direction) {
        mDirection = direction;
    }


    private int mDirection = DIRECTION_LEFT;
    private String mText = "小陆，学习学习";
    private Paint mPaint;
    private int mTextSize = 30;//sp2px(30);

    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;
    private Rect mTextBound = new Rect();
    private int mTextWidth;
    private int mTextHeight;

    private int mRealWidth;
    private float mProgress;

    public ColorTrackView(Context context) {
        super(context);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackView);
        mText = typedArray.getString(R.styleable.ColorTrackView_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ColorTrackView_text_size,mTextSize);
        mTextOriginColor = typedArray.getColor(R.styleable.ColorTrackView_text_origin_color,mTextOriginColor);
        mTextChangeColor = typedArray.getColor(R.styleable.ColorTrackView_text_change_color,mTextChangeColor);
        mProgress = typedArray.getFloat(R.styleable.ColorTrackView_progress,mProgress);
        mDirection = typedArray.getInt(R.styleable.ColorTrackView_direction,mDirection);
        typedArray.recycle();
        mPaint.setTextSize(mTextSize);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void measureText() {
        mTextWidth = (int) mPaint.measureText(mText);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.top);

        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextHeight = mTextBound.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureText();
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextStartX = getMeasuredWidth() / 2 - mTextWidth / 2;
        mTextStartY = getMeasuredHeight() / 2 - mTextHeight / 2;
    }

    private int measureHeight(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextBound.height();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result,val):result;
        return result + getPaddingTop()+ getPaddingBottom();
    }

    private int measureWidth(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
//                result = mTextBound.width();
                result = mTextWidth;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val):result;
        return result + getPaddingLeft() + getPaddingRight();
    }

    public void reverseColor() {
        int tmp = mTextOriginColor;
        mTextOriginColor = mTextChangeColor;
        mTextChangeColor = tmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,"onDraw");
        super.onDraw(canvas);
        int r = (int) (mProgress* mTextWidth +mTextStartX );

        if(mDirection == DIRECTION_LEFT) {
            drawChangeLeft(canvas, r);
            drawOriginLeft(canvas, r);
        }else {
            drawOriginRight(canvas, r);
            drawChangeRight(canvas, r);
        }
    }

    private void drawChangeRight(Canvas canvas, int r) {
        drawText(canvas, mTextChangeColor, (int) (mTextStartX +(1-mProgress)*mTextWidth), mTextStartX+mTextWidth );
    }
    private void drawOriginRight(Canvas canvas, int r) {
        drawText(canvas, mTextOriginColor, mTextStartX, (int) (mTextStartX +(1-mProgress)*mTextWidth) );
    }

    private void drawChangeLeft(Canvas canvas, int r) {
        drawText(canvas, mTextChangeColor, mTextStartX, (int) (mTextStartX + mProgress * mTextWidth) );
    }

    private void drawOriginLeft(Canvas canvas, int r) {
        drawText(canvas, mTextOriginColor, (int) (mTextStartX + mProgress * mTextWidth), mTextStartX +mTextWidth );
    }

    private void drawText(Canvas canvas , int color , int startX , int endX) {
        mPaint.setColor(color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());
        canvas.drawText(mText, mTextStartX, getMeasuredHeight() / 2
                + mTextBound.height() / 2, mPaint);
        canvas.restore();
    }

    public void setMProgress(float progress){
        mProgress = progress;
    }
}
