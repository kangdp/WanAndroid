package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

import java.util.List;

/**
 * 首页业务接口
 * author: 康栋普
 * date: 2018/2/22
 */

public interface IHomeModel {
    /**
     * 获取首页文章列表和Banner
     *
     * @param consumer
     * @param rxObserver
     */
    void getHomeData(int page, RxConsumer<List<BannerBean>> consumer, RxPageListObserver<ArticleBean> rxObserver);

}
