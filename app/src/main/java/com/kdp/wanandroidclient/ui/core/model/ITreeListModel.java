package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

/**
 * 知识体系列表业务接口
 * author: 康栋普
 * date: 2018/3/20
 */

public interface ITreeListModel {


    /**
     * 获取知识体系文章列表
     *
     * @param page 页码
     * @param cid 知识体系分类id
     * @param rxObserver
     */
    void getTreeList(int page, int cid, RxPageListObserver<Article> rxObserver);

}
