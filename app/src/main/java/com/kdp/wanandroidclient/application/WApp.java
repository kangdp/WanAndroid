package com.kdp.wanandroidclient.application;

import android.app.Application;

import com.kdp.wanandroidclient.BuildConfig;
import com.kdp.wanandroidclient.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * 玩Android
 * Created by 康栋普 on 2018/1/31.
 */

public class WApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化内存泄漏检测工具
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
        //开启debug模式
        LogUtils.isDebug = BuildConfig.LOG_DEBUG;
        //初始化App配置
        AppContext.initialize(this);
    }

}
