package com.kdp.wanandroidclient.application;

import android.content.Context;

import com.kdp.wanandroidclient.common.UrlConstainer;
import com.kdp.wanandroidclient.net.RxRetrofit;
import com.kdp.wanandroidclient.utils.PreUtils;

/**
 * author: 康栋普
 * date: 2018/3/13
 */

public class AppConfig {

    static void init(Context context){
        //初始化网络框架
        RxRetrofit.getInstance().initRxRetrofit(context, UrlConstainer.baseUrl);
        //初始化缓存
        PreUtils.init(context);
    }

}
