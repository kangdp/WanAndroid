package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxFunction;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IHomeModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: 康栋普
 * date: 2018/2/22
 */

public class HomeModel extends CommonModel implements IHomeModel {

    @Override
    public void getHomeData(final int page, RxConsumer<List<Banner>> consumer, final RxPageListObserver<Article> rxObserver) {
        doRxRequest()
                .getBanner()
                .doOnNext(consumer)
                .compose(RxSchedulers.<List<Banner>>io_main())
                .observeOn(Schedulers.io())
                .flatMap(new RxFunction<List<Banner>,PageListData<Article>>() {
                    @Override
                    protected Observable<BaseBean<PageListData<Article>>> doOnNextRequest() {
                        return doRxRequest().getArticleList(page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxObserver);
    }


}
