package com.flyco.pageindicator.anim.select;

import android.view.View;

import com.flyco.pageindicator.anim.base.IndicatorBaseAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class ZoomInEnter extends IndicatorBaseAnimator {
    public ZoomInEnter() {
        this.duration = 200;
    }

    public void setAnimation(View view) {
        this.animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.0F, 1.5F}),
                ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.0F, 1.5F})});
    }
}
