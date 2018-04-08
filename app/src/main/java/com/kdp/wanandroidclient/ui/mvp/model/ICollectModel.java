package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.net.callback.RxObserver;

/**
 * author: 康栋普
 * date: 2018/2/26
 */

public interface ICollectModel {

    /**
     * 收藏文章
     *
     * @param id       文章id
     * @param callback
     */
    void collectArticle(int id, RxObserver<String> callback);

    /**
     * 收藏站内文章
     *
     * @param id       文章id
     * @param callback
     */
    void collectInSideArticle(int id, RxObserver<String> callback);

    /**
     * 取消收藏文章
     *
     * @param id       文章id
     * @param callback
     */
    void unCollectArticle(int id, RxObserver<String> callback);

}
