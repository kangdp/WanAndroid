package com.kdp.wanandroidclient.ui.main;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.ui.core.view.IListDataView;

import java.util.List;

/**
 * 搜索协约类
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchContract {
    interface ISearchPresenter {
        void search();

        void getHotWord();

        void getFriend();

        void collectInsideArticle();

        void unCollectArticle();
    }

    interface ISearchView extends IListDataView<ArticleBean> {
        String getKeyword();

        void setHotwordData(List<HotwordBean> mHotwordDatas);

        void setFriendData(List<FriendBean> mFriendListDatas);
    }
}
