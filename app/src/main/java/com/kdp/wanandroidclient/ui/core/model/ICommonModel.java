package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.net.callback.RxObserver;

/**
 * 通用业务接口
 * author: 康栋普
 * date: 2018/2/26
 */

public interface ICommonModel {

    /**
     * 收藏文章
     *
     * @param id 文章id
     * @param callback
     */
    void collectArticle(int id, RxObserver<String> callback);


    /**
     * 取消收藏文章
     *
     * @param id 文章id
     * @param callback
     */
    void unCollectArticle(int id, RxObserver<String> callback);

}
