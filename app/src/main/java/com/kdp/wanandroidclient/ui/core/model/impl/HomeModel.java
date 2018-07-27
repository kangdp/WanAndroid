package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
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
    public void getHomeData(final int page, RxConsumer<List<BannerBean>> consumer, final RxPageListObserver<ArticleBean> rxObserver) {
        doRxRequest()
                .getBanner()
                .compose(RxSchedulers.io_main())
                .doOnNext(consumer)
                .observeOn(Schedulers.io())
                .flatMap(new RxFunction<List<BannerBean>,ArticleBean>() {
                    @Override
                    protected Observable doOnNextRequest() {
                        return doRxRequest().getArticleList(page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxObserver);
    }


}
