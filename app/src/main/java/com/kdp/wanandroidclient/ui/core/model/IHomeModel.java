package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.HomeData;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;

/**
 * 首页业务接口
 * author: 康栋普
 * date: 2018/2/22
 */

public interface IHomeModel {
    /**
     * 获取首页banner、置顶文章、列表文章
     * @param page 页码
     * @param function3
     * @param rxObserver
     */
    void getHomeData(int page, Function3<BaseBean<List<Banner>>, BaseBean<List<Article>>, BaseBean<PageListData<Article>>, HomeData> function3, DisposableObserver<HomeData> rxObserver);

    /**
     * 获取更多文章
     * @param page 页码
     * @param rxPageListObserver
     */
    void getMoreArticleList(int page,RxPageListObserver<Article> rxPageListObserver);


    Observable<BaseBean<List<Banner>>> getBannerObservable();
    Observable<BaseBean<List<Article>>> getHomeTopObservable();
    Observable<BaseBean<PageListData<Article>>> getHomeListObservable(int page);

}
