package com.flyco.pageindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.flyco.pageindicator.R;
import com.flyco.pageindicator.indicator.base.PageIndicator;

import java.util.ArrayList;

/** A pratice demo use GradientDrawable to realize the effect of JakeWharton's CirclePageIndicator */
public class RoundCornerIndicaor extends View implements PageIndicator {
    private Context context;
    private ViewPager vp;
    private ArrayList<GradientDrawable> unselectDrawables = new ArrayList<>();
    private ArrayList<Rect> unselectRects = new ArrayList<>();
    private GradientDrawable selectDrawable = new GradientDrawable();
    private Rect selectRect = new Rect();
    private int count;
    private int currentItem;
    private float positionOffset;
    private int indicatorWidth;
    private int indicatorHeight;
    private int indicatorGap;
    private int cornerRadius;
    private int selectColor;
    private int unselectColor;
    private int strokeWidth;
    private int strokeColor;
    private boolean isSnap;

    public RoundCornerIndicaor(Context context) {
        this(context, null);
    }

    public RoundCornerIndicaor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerIndicaor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerIndicaor);
        indicatorWidth = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicaor_rci_width, dp2px(6));
        indicatorHeight = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicaor_rci_height, dp2px(6));
        indicatorGap = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicaor_rci_gap, dp2px(8));
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicaor_rci_cornerRadius, dp2px(3));
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundCornerIndicaor_rci_strokeWidth, dp2px(0));
        selectColor = ta.getColor(R.styleable.RoundCornerIndicaor_rci_selectColor, Color.parseColor("#ffffff"));
        unselectColor = ta.getColor(R.styleable.RoundCornerIndicaor_rci_unselectColor, Color.parseColor("#88ffffff"));
        strokeColor = ta.getColor(R.styleable.RoundCornerIndicaor_rci_strokeColor, Color.parseColor("#ffffff"));
        isSnap = ta.getBoolean(R.styleable.RoundCornerIndicaor_rci_isSnap, false);

        ta.recycle();
    }

    @Override
    public void setViewPager(ViewPager vp) {
        if (isValid(vp)) {
            this.vp = vp;
            this.count = vp.getAdapter().getCount();
            vp.removeOnPageChangeListener(this);
            vp.addOnPageChangeListener(this);

            unselectDrawables.clear();
            unselectRects.clear();
            for (int i = 0; i < count; i++) {
                unselectDrawables.add(new GradientDrawable());
                unselectRects.add(new Rect());
            }

            invalidate();
        }
    }

    @Override
    public void setViewPager(ViewPager vp, int realCount) {
        if (isValid(vp)) {
            this.vp = vp;
            this.count = realCount;
            vp.removeOnPageChangeListener(this);
            vp.addOnPageChangeListener(this);

            unselectDrawables.clear();
            unselectRects.clear();
            for (int i = 0; i < count; i++) {
                unselectDrawables.add(new GradientDrawable());
                unselectRects.add(new Rect());
            }

            invalidate();
        }
    }

    public void setCurrentItem(int item) {
        if (isValid(vp)) {
            vp.setCurrentItem(item);
        }
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
        invalidate();
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        invalidate();
    }

    public void setIndicatorGap(int indicatorGap) {
        this.indicatorGap = indicatorGap;
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidate();
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        invalidate();
    }

    public void setUnselectColor(int unselectColor) {
        this.unselectColor = unselectColor;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public void setIsSnap(boolean isSnap) {
        this.isSnap = isSnap;
    }

    public int getCount() {
        return count;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public int getIndicatorWidth() {
        return indicatorWidth;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public int getIndicatorGap() {
        return indicatorGap;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public int getUnselectColor() {
        return unselectColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public boolean isSnap() {
        return isSnap;
    }


    private boolean isValid(ViewPager vp) {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL!");
        }

        if (vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager adapter can not be NULL!");
        }

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (!isSnap) {
            /**
             * position:当前View的位置
             * positionOffset:当前View的偏移量比例.[0,1)
             */
            currentItem = position;
            this.positionOffset = positionOffset;
            invalidate();
        }
    }

    /** 在ViewPager.OnPageChangeListener的onPageSelected方法中调用,不要同时调用onPageScrolled,会出现闪烁情况 */
    public void onPageSelected(int position) {
        if (isSnap) {
            currentItem = position;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (count <= 0) {
            return;
        }

        int verticalOffset = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - indicatorHeight / 2;
        int indicatorLayoutWidth = indicatorWidth * count + indicatorGap * (count - 1);
        int horizontalOffset = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 - indicatorLayoutWidth / 2;

        drawUnselect(canvas, count, verticalOffset, horizontalOffset);
        drawSelect(canvas, verticalOffset, horizontalOffset);
    }

    private void drawUnselect(Canvas canvas, int count, int verticalOffset, int horizontalOffset) {
        for (int i = 0; i < count; i++) {
            Rect rect = unselectRects.get(i);
            rect.left = horizontalOffset + (indicatorWidth + indicatorGap) * i;
            rect.top = verticalOffset;
            rect.right = rect.left + indicatorWidth;
            rect.bottom = rect.top + indicatorHeight;

            GradientDrawable unselectDrawable = unselectDrawables.get(i);
            unselectDrawable.setCornerRadius(cornerRadius);
            unselectDrawable.setColor(unselectColor);
            unselectDrawable.setStroke(strokeWidth, strokeColor);
            unselectDrawable.setBounds(rect);
            unselectDrawable.draw(canvas);
        }
    }

    private void drawSelect(Canvas canvas, int verticalOffset, int horizontalOffset) {
        int delta = (int) ((indicatorGap + indicatorWidth) * (isSnap ? 0 : positionOffset));

        selectRect.left = horizontalOffset + (indicatorWidth + indicatorGap) * currentItem + delta;
        selectRect.top = verticalOffset;
        selectRect.right = selectRect.left + indicatorWidth;
        selectRect.bottom = selectRect.top + indicatorHeight;

        selectDrawable.setCornerRadius(cornerRadius);
        selectDrawable.setColor(selectColor);
        selectDrawable.setBounds(selectRect);
        selectDrawable.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /** 确定View的宽度大小 */
    private int measureWidth(int widthMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY || count == 0) {//大小确定,直接使用
            result = specSize;
        } else {
            int padding = getPaddingLeft() + getPaddingRight();
            result = padding + indicatorWidth * count + indicatorGap * (count - 1);
            //如果父视图的测量要求为AT_MOST,即限定了一个最大值,则再从系统建议值和自己计算值中去一个较小值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /** 确定View的高度大小 */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//大小确定,直接使用
            result = specSize;
        } else {
            int padding = getPaddingTop() + getPaddingBottom();
            result = padding + indicatorHeight;
            //如果父视图的测量要求为AT_MOST,即限定了一个最大值,则再从系统建议值和自己计算值中去一个较小值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("currentItem", currentItem);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            currentItem = bundle.getInt("currentItem");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
