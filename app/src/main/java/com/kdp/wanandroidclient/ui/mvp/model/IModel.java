package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.api.ApiServer;


public interface IModel {
    /**
     * 使用RxRetrofit请求数据
     *
     * @return
     */
    ApiServer doRxRequest();
}
