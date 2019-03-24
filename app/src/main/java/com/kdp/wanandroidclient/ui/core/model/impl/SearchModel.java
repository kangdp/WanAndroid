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
    @Override
    public void searchArticle(int page, String keyword, RxPageListObserver<Article> rxObserver) {
        LogUtils.e(page+"");
        doRxRequest()
                .search(page, keyword)
                .compose(RxSchedulers.<PageListData<Article>>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getHotWord(RxObserver<List<Hotword>> observable) {
        doRxRequest()
                .getHotKeyword()
                .compose(RxSchedulers.<List<Hotword>>io_main())
                .subscribe(observable);
    }

    @Override
    public void getFriend(RxObserver<List<Friend>> rxObserver) {
        doRxRequest()
                .getFriend()
                .compose(RxSchedulers.<List<Friend>>io_main())
                .subscribe(rxObserver);
    }
}
