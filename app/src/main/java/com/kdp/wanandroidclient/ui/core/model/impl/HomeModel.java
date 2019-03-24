package com.kdp.wanandroidclient.ui.core.model.impl;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.HomeData;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxFunction;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IHomeModel;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author: 康栋普
 * date: 2018/2/22
 */

public class HomeModel extends CommonModel implements IHomeModel {

    /**
     * 获取首页banner、置顶文章、列表文章
     * @param page
     * @param function3
     * @param rxObserver
     */
    @Override
    public void getHomeData(int page, Function3<BaseBean<List<Banner>>, BaseBean<List<Article>>, BaseBean<PageListData<Article>>, HomeData> function3, DisposableObserver<HomeData> rxObserver) {
        Observable<BaseBean<List<Banner>>> bannerObservable = getBannerObservable();
        Observable<BaseBean<List<Article>>> homeTopObservable = getHomeTopObservable();
        Observable<BaseBean<PageListData<Article>>> homeObservable = getHomeListObservable(page);
        Observable.zip(bannerObservable, homeTopObservable, homeObservable, function3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxObserver);
    }
    /**
     * 获取列表文章
     * @param page
     * @param rxPageListObserver
     */
    @Override
    public void getHomeList(int page, RxPageListObserver<Article> rxPageListObserver) {
        doRxRequest().getHomeList(page)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxPageListObserver);
    }

    @Override
    public Observable<BaseBean<List<Banner>>> getBannerObservable() {
        return  doRxRequest().getBanner().subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<BaseBean<List<Article>>> getHomeTopObservable() {
        return doRxRequest().getHomeTopList().subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<BaseBean<PageListData<Article>>> getHomeListObservable(int page) {
        return doRxRequest().getHomeList(page);
    }

//    @SuppressLint("CheckResult")
//    @Override
//    public void getHomeData(final int page, RxConsumer<List<Banner>> consumer,RxPageListObserver<Article> rxObserver) {
//        doRxRequest()
//                .getBanner()
//                .doOnNext(consumer)
//                .compose(RxSchedulers.<List<Banner>>io_main())
//                .observeOn(Schedulers.io())
//                .flatMap(new RxFunction<List<Banner>,PageListData<Article>>() {
//                    @Override
//                    protected Observable<BaseBean<PageListData<Article>>> doOnNextRequest() {
//                        return doRxRequest().getArticleList(page);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(rxObserver);
//        final Gson gson = new Gson();
//        Observable<BaseBean<List<Banner>>> bannerObservable = doRxRequest().getBanner().subscribeOn(Schedulers.newThread());
//        Observable<BaseBean<List<Article>>> homeTopListObservable = doRxRequest().getHomeTopList().subscribeOn(Schedulers.newThread());
//        Observable<BaseBean<PageListData<Article>>> homeListObservable = doRxRequest().getArticleList(page).subscribeOn(Schedulers.newThread());
//        Observable.zip(bannerObservable, homeTopListObservable, homeListObservable, new Function3<BaseBean<List<Banner>>, BaseBean<List<Article>>, BaseBean<PageListData<Article>>, Object>() {
//            @Override
//            public Object apply(BaseBean<List<Banner>> listBanner, BaseBean<List<Article>> listArticle, BaseBean<PageListData<Article>> pageListDataBaseBean) throws Exception {
//                Log.e(HomeModel.class.getSimpleName(), "apply: " + gson.toJson(listBanner));
//                return listBanner;
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//
//            }
//        });

//    }

}
