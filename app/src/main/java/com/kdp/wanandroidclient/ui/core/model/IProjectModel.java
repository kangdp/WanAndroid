package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

public interface IProjectModel {

    /**
     * 获取项目列表
     * @param page
     * @param cid
     * @param rxPageListObserver
     */
    void getProjectList(int page, int cid, RxPageListObserver<Article> rxPageListObserver);
}
