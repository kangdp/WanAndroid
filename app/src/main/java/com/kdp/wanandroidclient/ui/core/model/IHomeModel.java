package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
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
    void getHomeData(int page, RxConsumer<List<Banner>> consumer, RxPageListObserver<Article> rxObserver);

}
