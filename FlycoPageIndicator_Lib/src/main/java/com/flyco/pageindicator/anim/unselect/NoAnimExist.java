package com.flyco.pageindicator.anim.unselect;

import android.view.View;

import com.flyco.pageindicator.anim.base.IndicatorBaseAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class NoAnimExist extends IndicatorBaseAnimator {
    public NoAnimExist() {
        this.duration = 200;
    }

    public void setAnimation(View view) {
        this.animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "alpha", 1, 1)});
    }
}
