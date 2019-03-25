package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.ui.core.model.ICommonModel;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;

/**
 * author: 康栋普
 * date: 2018/2/26
 */

public class CommonModel extends BaseModel implements ICommonModel {
    /**
     * 收藏
     * @param id 文章id
     * @param callback
     */
    @Override
    public void collectArticle(int id, RxObserver<String> callback) {
        doRxRequest().
                collectArticle(id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);

    }

    /**
     * 取消收藏
     * @param id 文章id
     * @param callback
     */
    @Override
    public void unCollectArticle(int id, RxObserver<String> callback) {
        doRxRequest()
                .unCollectArticle(id)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }


}
