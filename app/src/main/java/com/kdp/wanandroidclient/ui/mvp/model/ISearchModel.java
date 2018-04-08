package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public interface ISearchModel {

    /**
     * 搜索文章
     *
     * @param page
     * @param keyword            关键词
     * @param rxPageListObserver
     */
    void searchArticle(int page, String keyword, RxPageListObserver<ArticleBean> rxPageListObserver);

    /**
     * 搜索热词
     *
     * @param rxObserver
     */
    void getHotWord(RxObserver<List<HotwordBean>> rxObserver);

    /**
     * 常用网站
     *
     * @param rxObserver
     */
    void getFriend(RxObserver<List<FriendBean>> rxObserver);
}
