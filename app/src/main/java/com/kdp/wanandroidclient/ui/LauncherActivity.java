package com.kdp.wanandroidclient.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.base.BasePresenterActivity;
import com.kdp.wanandroidclient.ui.logon.LogonContract;
import com.kdp.wanandroidclient.ui.logon.LogonPresenter;
import com.kdp.wanandroidclient.ui.main.MainActivity;

import java.lang.ref.WeakReference;


/**
 * 启动页、程序入口
 * Created by 康栋普 on 2018/1/31.
 */

public class LauncherActivity extends BasePresenterActivity<LogonPresenter> implements LogonContract.ILoginRegisterView {
    private User user;
    private Handler mHandler;
    private DelayRunnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        //倒计时
        startCountdown();
    }

    private void startCountdown() {
        mHandler = new Handler();
        mRunnable = new DelayRunnable(this);
        mHandler.postDelayed(mRunnable, 2000);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //自动登录
    private void autoLogin() {
        if (UserInfoManager.isLogin()) {
            //自动登录
            user = UserInfoManager.getUserInfo();
            if (user != null)
                mPresenter.login();
        }
        startToActivity();
    }


    private static class DelayRunnable implements Runnable {
        private WeakReference<LauncherActivity> mWeakReference;

        DelayRunnable(LauncherActivity instance) {
            mWeakReference = new WeakReference<>(instance);
        }

        @Override
        public void run() {
            LauncherActivity instance = mWeakReference.get();
            if (instance == null) return;
            instance.autoLogin();
        }
    }

    //进入首页
    private void startToActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected LogonPresenter createPresenter() {
        return new LogonPresenter();
    }

    @Override
    public String getUserName() {
        return user.getUsername();
    }

    @Override
    public String getPassWord() {
        return user.getPassword();
    }


    @Override
    public void showResult(String msg) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}
