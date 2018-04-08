package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.net.NetConfig;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 用来处理嵌套请求的操作
 * author: 康栋普
 * date: 2018/3/7
 */

public abstract class RxFunction<T, R> implements Function<BaseBean<T>, ObservableSource<BaseBean<R>>> {

    @Override
    public ObservableSource<BaseBean<R>> apply(BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.errorCode == NetConfig.REQUEST_SUCCESS){
            return doOnNextRequest();
        }
        return null;
    }

    /**
     * doOnNext
     *
     * @return
     */
    protected abstract Observable<BaseBean<R>> doOnNextRequest();

}
