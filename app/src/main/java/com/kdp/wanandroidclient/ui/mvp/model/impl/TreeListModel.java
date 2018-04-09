package com.kdp.wanandroidclient.ui.mvp.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.ITreeListModel;

/**
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListModel extends CommonModel implements ITreeListModel {

    @Override
    public void getTreeList(int page,int cid,RxPageListObserver<ArticleBean> rxObserver) {
        doRxRequest()
                .getTreeList(page, cid)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);

    }
}
