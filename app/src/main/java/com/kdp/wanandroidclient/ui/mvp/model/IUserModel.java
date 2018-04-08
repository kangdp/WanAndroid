package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public interface IUserModel {
    /**
     * 收藏的文章列表
     * @param page
     * @param rxObserver
     */
    void getCollectArticleList(int page, RxPageListObserver<ArticleBean> rxObserver);


    /**
     * 删除收藏
     *
     * @param id       收藏列表的文章id
     * @param originId 首页列表的文章id
     * @param callback
     */
    void deleteCollectArticle(int id, int originId, RxObserver<String> callback);
}
