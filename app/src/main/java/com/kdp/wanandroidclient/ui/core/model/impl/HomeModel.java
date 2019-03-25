package com.kdp.wanandroidclient.ui.core.model.impl;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.HomeData;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IHomeModel;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public void getMoreArticleList(int page, RxPageListObserver<Article> rxPageListObserver) {
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
}
