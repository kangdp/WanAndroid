package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.bean.PageListDataBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.ISearchModel;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchModel extends CommonModel implements ISearchModel {
    @Override
    public void searchArticle(int page, String keyword, RxPageListObserver<ArticleBean> rxObserver) {
        LogUtils.e(page+"");
        doRxRequest()
                .search(page, keyword)
                .compose(RxSchedulers.<PageListDataBean<ArticleBean>>io_main())
                .subscribe(rxObserver);
    }

    @Override
    public void getHotWord(RxObserver<List<HotwordBean>> observable) {
        doRxRequest()
                .getHotKeyword()
                .compose(RxSchedulers.<List<HotwordBean>>io_main())
                .subscribe(observable);
    }

    @Override
    public void getFriend(RxObserver<List<FriendBean>> rxObserver) {
        doRxRequest()
                .getFriend()
                .compose(RxSchedulers.<List<FriendBean>>io_main())
                .subscribe(rxObserver);
    }
}
