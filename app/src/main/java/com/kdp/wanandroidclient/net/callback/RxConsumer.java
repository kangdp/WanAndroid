package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.net.NetConfig;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * author: 康栋普
 * date: 2018/3/7
 */

public abstract class RxConsumer<T> implements Consumer<BaseBean<T>> {

    @Override
    public void accept(@NonNull BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.errorCode == NetConfig.REQUEST_SUCCESS){
            onSuccess(tBaseBean.data);
        }else {
            onFail(tBaseBean.errorMsg);
        }
    }

    protected abstract void onFail(String errorMsg);

    protected abstract void onSuccess(T data);
}