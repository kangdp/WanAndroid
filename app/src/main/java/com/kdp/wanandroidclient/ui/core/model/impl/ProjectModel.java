package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IProjectModel;

public class ProjectModel extends CommonModel implements IProjectModel {
    /**
     * 获取项目列表
     * @param page 页码
     * @param cid 分类id
     * @param rxPageListObserver
     */
    @Override
    public void getProjectList(int page, int cid, RxPageListObserver<Article> rxPageListObserver) {
        doRxRequest()
                .getProjectList(page,cid)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxPageListObserver);
    }
}
