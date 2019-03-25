package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

/**
 * 和用户相关的业务接口
 * author: 康栋普
 * date: 2018/3/21
 */

public interface IUserModel {
    /**
     * 收藏的文章列表
     * @param page 页码
     * @param rxObserver
     */
    void getCollectArticleList(int page, RxPageListObserver<Article> rxObserver);


    /**
     * 删除收藏
     *
     * @param id       收藏列表的文章id
     * @param originId 首页列表的文章id
     * @param callback
     */
    void deleteCollectArticle(int id, int originId, RxObserver<String> callback);
}
