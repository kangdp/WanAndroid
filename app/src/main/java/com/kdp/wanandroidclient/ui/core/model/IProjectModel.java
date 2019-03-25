package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

public interface IProjectModel {

    /**
     * 获取项目列表
     * @param page 页码
     * @param cid 项目分类id
     * @param rxPageListObserver
     */
    void getProjectList(int page, int cid, RxPageListObserver<Article> rxPageListObserver);
}
