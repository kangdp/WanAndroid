package com.kdp.wanandroidclient.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Banner广告
 * author: 康栋普
 * date: 2018/3/7
 */

public class BannerViewPager extends ViewPager {

    private Handler mHandler;
    private TaskRunnable mTaskRunnable;
    private BannerViewPager instance;
    public static boolean mIsRunning = false; //是否正在执行

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        instance = this;
        //滚动监听
        //空闲状态
        //手指触摸滑动
        //手指松开,惯性滑动
        OnPageChangeListener mOnPagerChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE: //空闲状态
                        start();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING: //手指触摸滑动
                        stop();
                        break;
                    case SCROLL_STATE_SETTLING: //手指松开,惯性滑动
                        break;
                }
            }
        };
        addOnPageChangeListener(mOnPagerChangeListener);
        //修改ViewPager的滚动速度
        setViewPagerDuration();
    }

    /**
     * 开启任务
     */
    private void startTimingTask() {
        if (mHandler == null && !mIsRunning) {
            mHandler = new Handler();
            mTaskRunnable = new TaskRunnable(instance);
            mHandler.postDelayed(mTaskRunnable, 6000);
            mIsRunning = true;
        }
    }


    /**
     * 结束任务
     */
    private void stopTimingTask() {
        if (mHandler != null && mIsRunning) {
            mHandler.removeCallbacks(mTaskRunnable);
            mHandler = null;
            mIsRunning = false;
        }

    }

    private static class TaskRunnable implements Runnable {

        private WeakReference<BannerViewPager> weakReference;

        TaskRunnable(BannerViewPager bannerViewPager) {
            this.weakReference = new WeakReference<>(bannerViewPager);
        }

        @Override
        public void run() {
            //执行切换任务
            BannerViewPager instance = weakReference.get();
            if (instance == null) return;
            instance.setCurrentItem();
        }
    }


    private void setCurrentItem() {
        setCurrentItem(getCurrentItem() + 1, true);
        mHandler.postDelayed(mTaskRunnable, 6000);
    }

    public void start() {
        startTimingTask();
    }

    public void stop() {
        stopTimingTask();
    }


    private class FixedSpeedScroll extends Scroller {

        private int mDuration = 750;//毫秒

        private FixedSpeedScroll(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);

        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //防止ViewPager可见时第一次切换无动画效果
        //滚动监听
        setFirstLayout(false);
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     *
     * @param isFirstLayout false
     */
    private void setFirstLayout(boolean isFirstLayout) {
        try {
            Class<?> clazz = Class.forName("android.support.v4.view.ViewPager");
            Field field = clazz.getDeclaredField("mFirstLayout");
            field.setAccessible(true);
            field.setBoolean(this, isFirstLayout);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改ViewPager滑动速度
     */
    private void setViewPagerDuration() {
        try {
            Class<?> clazz = Class.forName("android.support.v4.view.ViewPager");
            FixedSpeedScroll mScriller = new FixedSpeedScroll(getContext());
            Field field = clazz.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScriller);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
