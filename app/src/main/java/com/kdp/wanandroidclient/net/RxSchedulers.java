package com.kdp.wanandroidclient.net;

import com.kdp.wanandroidclient.bean.BaseBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: 康栋普
 * date: 2018/1/30
 */

public class RxSchedulers{

    /**
     * 指定被观察者为io线程
     * 观察者为主线程
     */

    public static <T>ObservableTransformer<BaseBean<T>,BaseBean<T>> io_main() {
        return new ObservableTransformer<BaseBean<T>, BaseBean<T>>() {
            @Override
            public ObservableSource<BaseBean<T>> apply(Observable<BaseBean<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
