package com.kdp.wanandroidclient.utils;

import android.animation.Animator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;

/***
 * @author kdp
 * @date 2019/3/27 16:41
 * @description
 */
public class ViewAnimatorHelper {
    private boolean isAnimating;
    private ViewPropertyAnimator viewPropertyAnimator;
    private View view;
    public  void bindView(View view){
        if (view == null)
            throw new NullPointerException("The view is cannot null");
        this.view = view;
        if (viewPropertyAnimator == null){
            viewPropertyAnimator = view.animate();
            viewPropertyAnimator.setDuration(300);
            viewPropertyAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        }
    }

    public void showFloatActionButton(){
        view.setVisibility(View.VISIBLE);
        viewPropertyAnimator.scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setListener(null);
    }

    public void hideFloatActionButton(){
        viewPropertyAnimator.scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setListener(animationListener);
    }

    private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimating = false;
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnimating = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };


    public boolean isAnimating() {
        return isAnimating;
    }
}
