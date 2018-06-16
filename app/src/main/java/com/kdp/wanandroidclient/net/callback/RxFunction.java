package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.net.NetConfig;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 用来处理嵌套请求
 * author: 康栋普
 * date: 2018/3/7
 */

public abstract class RxFunction<T, R> implements Function<BaseBean<T>, Observable<BaseBean<R>>> {

    @Override
    public Observable<BaseBean<R>> apply(BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.errorCode == NetConfig.REQUEST_SUCCESS){
            return doOnNextRequest();
        }
        return null;
    }

    protected abstract Observable<BaseBean<R>> doOnNextRequest();

}
