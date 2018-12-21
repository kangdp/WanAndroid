package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.PageListDataBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IUserModel;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public class UserModel extends CommonModel implements IUserModel {
    @Override
    public void getCollectArticleList(int page, RxPageListObserver<ArticleBean> rxObserver) {
        doRxRequest()
                .getCollectArticleList(page)
                .compose(RxSchedulers.<PageListDataBean<ArticleBean>>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void deleteCollectArticle(int id, int originId, RxObserver<String> callback) {
        doRxRequest()
                .deleteCollectArticle(id, originId)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }
}
