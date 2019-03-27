package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;

/***
 * @author kdp
 * @date 2019/3/27 9:27
 * @description
 */
public interface IChapterListModel {

    /**
     * 获取公众号文章列表
     * @param page 页码
     * @param cid 公众号cid
     * @param rxPageListObserver
     */
    void getChapterArticleList(int page, int cid, RxPageListObserver<Article> rxPageListObserver);
}
