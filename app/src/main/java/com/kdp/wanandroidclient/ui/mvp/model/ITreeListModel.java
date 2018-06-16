package com.kdp.wanandroidclient.ui.mvp.model;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

/**
 * 知识体系列表业务接口
 * author: 康栋普
 * date: 2018/3/20
 */

public interface ITreeListModel {


    /**
     * 获取知识体系列表
     *
     * @param page
     * @param cid
     * @param rxObserver
     */
    void getTreeList(int page, int cid, RxPageListObserver<ArticleBean> rxObserver);

}
