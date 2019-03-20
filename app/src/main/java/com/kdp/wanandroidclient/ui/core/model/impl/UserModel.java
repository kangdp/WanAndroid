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
    @Override
    public void getCollectArticleList(int page, RxPageListObserver<Article> rxObserver) {
        doRxRequest()
                .getCollectArticleList(page)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
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
