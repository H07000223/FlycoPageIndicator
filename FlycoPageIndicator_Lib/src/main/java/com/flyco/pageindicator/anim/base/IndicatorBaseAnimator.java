package com.flyco.pageindicator.anim.base;

import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

public abstract class IndicatorBaseAnimator {
    protected long duration = 200;
    protected AnimatorSet animatorSet = new AnimatorSet();
    private Interpolator interpolator;
    private long delay;
    private AnimatorListener listener;

    public abstract void setAnimation(View view);

    protected void start(final View view) {
        reset(view);
        setAnimation(view);

        animatorSet.setDuration(duration);
        if (interpolator != null) {
            animatorSet.setInterpolator(interpolator);
        }

        if (delay > 0) {
            animatorSet.setStartDelay(delay);
        }

        if (listener != null) {
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    listener.onAnimationStart(animator);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    listener.onAnimationRepeat(animator);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    listener.onAnimationEnd(animator);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    listener.onAnimationCancel(animator);
                }
            });
        }

        animatorSet.setTarget(view);
        animatorSet.start();
    }

    public static void reset(View view) {
        ViewHelper.setAlpha(view, 1);
        ViewHelper.setScaleX(view, 1);
        ViewHelper.setScaleY(view, 1);
        ViewHelper.setTranslationX(view, 0);
        ViewHelper.setTranslationY(view, 0);
        ViewHelper.setRotation(view, 0);
        ViewHelper.setRotationY(view, 0);
        ViewHelper.setRotationX(view, 0);
    }

    public IndicatorBaseAnimator duration(long duration) {
        this.duration = duration;
        return this;
    }

    public IndicatorBaseAnimator delay(long delay) {
        this.delay = delay;
        return this;
    }

    public IndicatorBaseAnimator interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public IndicatorBaseAnimator listener(AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public void playOn(View view) {
        start(view);
    }

    public interface AnimatorListener {
        void onAnimationStart(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationCancel(Animator animator);
    }
}
