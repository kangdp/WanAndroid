package com.kdp.wanandroidclient.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: 康栋普
 * date: 2018/1/30
 */

public class RxSchedulers {

    /**
     * 指定上游为io线程
     * 下游为主线程
     */

    public static ObservableTransformer io_main(){
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
