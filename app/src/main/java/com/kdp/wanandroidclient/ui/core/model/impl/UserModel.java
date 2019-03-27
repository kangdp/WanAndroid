package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IUserModel;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public class UserModel extends CommonModel implements IUserModel {
    /**
     * 收藏的文章列表
     * @param page 页码
     * @param rxObserver
     */
    @Override
    public void getCollectArticleList(int page, RxPageListObserver<Article> rxObserver) {
        doRxRequest()
                .getCollectArticleList(page)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxObserver);
    }

    /**
     * 删除收藏
     * @param id       收藏列表的文章id
     * @param originId 首页列表的文章id
     * @param callback
     */
    @Override
    public void deleteCollectArticle(int id, int originId, RxObserver<String> callback) {
        doRxRequest()
                .deleteCollectArticle(id, originId)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }
}
