package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.ITreeListModel;

/**
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListModel extends CommonModel implements ITreeListModel {

    /**
     * 获取知识体系文章列表
     * @param page 页码
     * @param cid 知识体系分类id
     * @param rxObserver
     */
    @Override
    public void getTreeList(int page,int cid,RxPageListObserver<Article> rxObserver) {
        doRxRequest()
                .getTreeList(page, cid)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxObserver);

    }
}
