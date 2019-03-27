package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.IChapterListModel;

/***
 * @author kdp
 * @date 2019/3/27 9:28
 * @description
 */
public class ChapterListModel extends CommonModel implements IChapterListModel {
    /**
     * 获取公众号文章列表
     * @param page 页码
     * @param cid 公众号cid
     * @param rxPageListObserver
     */
    @Override
    public void getChapterArticleList(int page, int cid, RxPageListObserver<Article> rxPageListObserver) {
        doRxRequest()
                .getChapterList(page,cid)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxPageListObserver);
    }
}
