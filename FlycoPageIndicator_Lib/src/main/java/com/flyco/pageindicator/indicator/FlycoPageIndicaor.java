package com.flyco.pageindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.pageindicator.R;
import com.flyco.pageindicator.anim.base.IndicatorBaseAnimator;
import com.flyco.pageindicator.indicator.base.PageIndicator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

public class FlycoPageIndicaor extends LinearLayout implements PageIndicator {
    private Context context;
    private ViewPager vp;
    private RelativeLayout rl_parent;
    private View selectView;
    private ArrayList<ImageView> indicatorViews = new ArrayList<>();
    private int count;

    private int currentItem;
    private int lastItem;
    private int indicatorWidth;
    private int indicatorHeight;
    private int indicatorGap;
    private int cornerRadius;
    private Drawable selectDrawable;
    private Drawable unSelectDrawable;
    private int strokeWidth;
    private int strokeColor;
    private boolean isSnap;

    private Class<? extends IndicatorBaseAnimator> selectAnimClass;
    private Class<? extends IndicatorBaseAnimator> unselectAnimClass;

    public FlycoPageIndicaor(Context context) {
        this(context, null);
    }

    public FlycoPageIndicaor(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setClipChildren(false);
        setClipToPadding(false);

        rl_parent = new RelativeLayout(context);
        rl_parent.setClipChildren(false);
        rl_parent.setClipToPadding(false);
        addView(rl_parent);

        setGravity(Gravity.CENTER);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlycoPageIndicaor);
        indicatorWidth = ta.getDimensionPixelSize(R.styleable.FlycoPageIndicaor_fpi_width, dp2px(6));
        indicatorHeight = ta.getDimensionPixelSize(R.styleable.FlycoPageIndicaor_fpi_height, dp2px(6));
        indicatorGap = ta.getDimensionPixelSize(R.styleable.FlycoPageIndicaor_fpi_gap, dp2px(8));
        cornerRadius = ta.getDimensionPixelSize(R.styleable.FlycoPageIndicaor_fpi_cornerRadius, dp2px(3));
        strokeWidth = ta.getDimensionPixelSize(R.styleable.FlycoPageIndicaor_fpi_strokeWidth, dp2px(0));
        strokeColor = ta.getColor(R.styleable.FlycoPageIndicaor_fpi_strokeColor, Color.parseColor("#ffffff"));
        isSnap = ta.getBoolean(R.styleable.FlycoPageIndicaor_fpi_isSnap, false);

        int selectColor = ta.getColor(R.styleable.FlycoPageIndicaor_fpi_selectColor, Color.parseColor("#ffffff"));
        int unselectColor = ta.getColor(R.styleable.FlycoPageIndicaor_fpi_unselectColor, Color.parseColor("#88ffffff"));
        int selectRes = ta.getResourceId(R.styleable.FlycoPageIndicaor_fpi_selectRes, 0);
        int unselectRes = ta.getResourceId(R.styleable.FlycoPageIndicaor_fpi_unselectRes, 0);
        ta.recycle();

        if (selectRes != 0) {
            this.selectDrawable = getResources().getDrawable(selectRes);
        } else {
            this.selectDrawable = getDrawable(selectColor, cornerRadius);
        }

        if (unselectRes != 0) {
            this.unSelectDrawable = getResources().getDrawable(unselectRes);
        } else {
            this.unSelectDrawable = getDrawable(unselectColor, cornerRadius);
        }
    }

    /** call before setViewPager. set indicator width, unit dp, default 6dp */
    public FlycoPageIndicaor setIndicatorWidth(float indicatorWidth) {
        this.indicatorWidth = dp2px(indicatorWidth);
        return this;
    }

    /** call before setViewPager. set indicator height, unit dp, default 6dp */
    public FlycoPageIndicaor setIndicatorHeight(float indicatorHeight) {
        this.indicatorHeight = dp2px(indicatorHeight);
        return this;
    }

    /** call before setViewPager. set gap between two indicators, unit dp, default 6dp */
    public FlycoPageIndicaor setIndicatorGap(float indicatorGap) {
        this.indicatorGap = dp2px(indicatorGap);
        return this;
    }

    /** call before setViewPager. set indicator select color, default "#ffffff" "#88ffffff" */
    public FlycoPageIndicaor setIndicatorSelectColor(int selectColor, int unselectColor) {
        this.selectDrawable = getDrawable(selectColor, cornerRadius);
        this.unSelectDrawable = getDrawable(unselectColor, cornerRadius);
        return this;
    }

    /** call before setViewPager. set indicator corner raduis, unit dp, default 3dp */
    public FlycoPageIndicaor setCornerRadius(float cornerRadius) {
        this.cornerRadius = dp2px(cornerRadius);
        return this;
    }

    /** call before setViewPager. set width of the stroke used to draw the indicators, unit dp, default 0dp */
    public FlycoPageIndicaor setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    /** call before setViewPager. set color of the stroke used to draw the indicators. default "#ffffff" */
    public FlycoPageIndicaor setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    /** call before setViewPager. Whether or not the selected indicator snaps to the indicators. default false */
    public FlycoPageIndicaor setIsSnap(boolean isSnap) {
        this.isSnap = isSnap;
        return this;
    }

    /** call before setViewPager. set indicator select anim. only valid when isSnap is true */
    public FlycoPageIndicaor setSelectAnimClass(Class<? extends IndicatorBaseAnimator> selectAnimClass) {
        this.selectAnimClass = selectAnimClass;
        return this;
    }

    /** call before setViewPager. set indicator unselect anim. only valid when isSnap is true */
    public FlycoPageIndicaor setUnselectAnimClass(Class<? extends IndicatorBaseAnimator> unselectAnimClass) {
        this.unselectAnimClass = unselectAnimClass;
        return this;
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

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public boolean isSnap() {
        return isSnap;
    }

    @Override
    public void setCurrentItem(int item) {
        if (isValid()) {
            vp.setCurrentItem(item);
        }
    }

    @Override
    public void setViewPager(ViewPager vp) {
        this.vp = vp;
        if (isValid()) {
            this.count = vp.getAdapter().getCount();
            vp.removeOnPageChangeListener(this);
            vp.addOnPageChangeListener(this);

            createIndicators();
        }
    }

    @Override
    public void setViewPager(ViewPager vp, int realCount) {
        this.vp = vp;
        if (isValid()) {
            this.count = realCount;
            vp.removeOnPageChangeListener(this);
            vp.addOnPageChangeListener(this);

            createIndicators();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isSnap) {
            /**
             * position:当前View的位置
             * positionOffset:当前View的偏移量比例.[0,1)
             */
            this.currentItem = position;

            float tranlationX = (indicatorWidth + indicatorGap) * (currentItem + positionOffset);
            ViewHelper.setTranslationX(selectView, tranlationX);

//            RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(indicatorWidth,
//                    indicatorHeight);
//            lp.leftMargin = (int) tranlationX;
//            selectView.setLayoutParams(lp);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (isSnap) {
            this.currentItem = position;

            for (int i = 0; i < indicatorViews.size(); i++) {
                indicatorViews.get(i).setImageDrawable(i == position ? selectDrawable : unSelectDrawable);
            }
            animSwitch(position);
            lastItem = position;
        }
    }

    private void animSwitch(int position) {
        try {
//            Log.d(TAG, "position--->" + position);
//            Log.d(TAG, "lastPositon--->" + lastPositon);
            if (selectAnimClass != null) {
                if (position == lastItem) {
                    selectAnimClass.newInstance().playOn(indicatorViews.get(position));
                } else {
                    selectAnimClass.newInstance().playOn(indicatorViews.get(position));
                    if (unselectAnimClass == null) {
                        selectAnimClass.newInstance().interpolator(new ReverseInterpolator()).playOn(indicatorViews.get(lastItem));
                    } else {
                        unselectAnimClass.newInstance().playOn(indicatorViews.get(lastItem));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void createIndicators() {
        if (count <= 0) {
            return;
        }

        indicatorViews.clear();
        rl_parent.removeAllViews();

        LinearLayout ll_unselect_views = new LinearLayout(context);
        rl_parent.addView(ll_unselect_views);

        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(context);
            iv.setImageDrawable(!isSnap ? unSelectDrawable : (currentItem == i ? selectDrawable : unSelectDrawable));
            LayoutParams lp = new LayoutParams(indicatorWidth,
                    indicatorHeight);
            lp.leftMargin = i == 0 ? 0 : indicatorGap;
            ll_unselect_views.addView(iv, lp);
            indicatorViews.add(iv);
        }

        if (!isSnap) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(indicatorWidth,
                    indicatorHeight);
            lp.leftMargin = (indicatorWidth + indicatorGap) * currentItem;
            selectView = new View(context);
            selectView.setBackgroundDrawable(selectDrawable);
            rl_parent.addView(selectView, lp);
        }

        animSwitch(currentItem);
    }

    private boolean isValid() {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL!");
        }

        if (vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager adapter can not be NULL!");
        }

        return true;
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    private GradientDrawable getDrawable(int color, float raduis) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(raduis);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setColor(color);

        return drawable;
    }


    private int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
}
