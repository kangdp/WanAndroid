package com.kdp.wanandroidclient.ui.mvp.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.IUserModel;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public class UserModel extends BaseModel implements IUserModel {
    @Override
    public void getCollectArticleList(int page, RxPageListObserver<ArticleBean> rxObserver) {
        doRxRequest()
                .getCollectArticleList(page)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void deleteCollectArticle(int id, int originId, RxObserver<String> callback) {
        doRxRequest()
                .deleteCollectArticle(id, originId)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }
}
