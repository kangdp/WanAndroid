package com.kdp.wanandroidclient.ui.home;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

import java.util.List;

/**
 * Home协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface HomeContract {
    interface IHomePresenter {
        void getHomeData();
        void getMoreArticleList();

        void collectArticle();

        void unCollectArticle();
    }

    interface IHomeView extends IPageLoadDataView<Article> {
        int getArticleId();
        void setBannerData(List<Banner> banner);
        void collect(boolean isCollect,String result);
    }
}
