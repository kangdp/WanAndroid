package com.kdp.wanandroidclient.ui.mvp.model.impl;

import com.kdp.wanandroidclient.ui.mvp.model.ICollectModel;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;

/**
 * author: 康栋普
 * date: 2018/2/26
 */

public class CollectModel extends BaseModel implements ICollectModel {
    @Override
    public void collectArticle(int id, RxObserver<String> callback) {
        doRxRequest().
                collectArticle(id)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void collectInSideArticle(int id, RxObserver<String> callback) {
        doRxRequest().
                collectInsideArticle(id)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void unCollectArticle(int id, RxObserver<String> callback) {
        doRxRequest()
                .unCollectArticle(id)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }


}
