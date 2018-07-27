package com.kdp.wanandroidclient.ui.home;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.ui.core.view.IListDataView;

import java.util.List;

/**
 * Home协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface HomeContract {
    interface IHomePresenter {
        void getHomeList();

        void collectArticle();

        void unCollectArticle();
    }

    interface IHomeView extends IListDataView<ArticleBean> {

        void setBannerData(List<BannerBean> banner);

    }
}
