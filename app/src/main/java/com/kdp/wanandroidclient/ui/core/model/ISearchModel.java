package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Friend;
import com.kdp.wanandroidclient.bean.Hotword;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

import java.util.List;

/**
 * 和搜索相关的业务接口
 * author: 康栋普
 * date: 2018/4/5
 */

public interface ISearchModel {

    /**
     * 搜索文章
     *
     * @param page 页码
     * @param keyword 关键词
     * @param rxPageListObserver
     */
    void searchArticle(int page, String keyword, RxPageListObserver<Article> rxPageListObserver);

    /**
     * 搜索热词
     *
     * @param rxObserver
     */
    void getHotWord(RxObserver<List<Hotword>> rxObserver);

    /**
     * 常用网站
     *
     * @param rxObserver
     */
    void getFriend(RxObserver<List<Friend>> rxObserver);
}
