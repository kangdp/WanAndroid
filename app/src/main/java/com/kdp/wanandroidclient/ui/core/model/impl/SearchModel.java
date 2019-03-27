package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Friend;
import com.kdp.wanandroidclient.bean.Hotword;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.ISearchModel;
import com.kdp.wanandroidclient.utils.LogUtils;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchModel extends CommonModel implements ISearchModel {
    /**
     * 搜索文章
     * @param page 页码
     * @param keyword 关键词
     * @param rxObserver
     */
    @Override
    public void searchArticle(int page, String keyword, RxPageListObserver<Article> rxObserver) {
        doRxRequest()
                .search(page, keyword)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxObserver);
    }

    /**
     * 搜索热词
     * @param observable
     */
    @Override
    public void getHotWord(RxObserver<List<Hotword>> observable) {
        doRxRequest()
                .getHotKeyword()
                .compose(RxSchedulers.<List<Hotword>>io_main())
                .subscribe(observable);
    }

    /**
     * 常用网站
     * @param rxObserver
     */
    @Override
    public void getFriend(RxObserver<List<Friend>> rxObserver) {
        doRxRequest()
                .getFriend()
                .compose(RxSchedulers.<List<Friend>>io_main())
                .subscribe(rxObserver);
    }
}
