package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.api.ApiServer;
import com.kdp.wanandroidclient.net.RxRetrofit;
import com.kdp.wanandroidclient.ui.core.model.IModel;


public class BaseModel implements IModel {

    @Override
    public ApiServer doRxRequest() {
        return RxRetrofit.Api();
    }
}
