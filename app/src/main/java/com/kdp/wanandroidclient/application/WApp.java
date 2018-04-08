package com.kdp.wanandroidclient.application;

import android.app.Application;

/**
 * Created by 康栋普 on 2018/1/31.
 */

public class WApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化App配置
        AppContext.initialize(this);
    }

}
