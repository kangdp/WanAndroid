package com.kdp.wanandroidclient.ui.mvp.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.ISearchModel;
import com.kdp.wanandroidclient.utils.LogUtils;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchModel extends CollectModel implements ISearchModel {
    @Override
    public void searchArticle(int page, String keyword, RxPageListObserver<ArticleBean> rxObserver) {
        LogUtils.e(page+"");
        doRxRequest()
                .search(page, keyword)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getHotWord(RxObserver<List<HotwordBean>> observable) {
        doRxRequest()
                .getHotKeyword()
                .compose(RxSchedulers.io_main())
                .subscribe(observable);
    }

    @Override
    public void getFriend(RxObserver<List<FriendBean>> rxObserver) {
        doRxRequest()
                .getFriend()
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }
}
