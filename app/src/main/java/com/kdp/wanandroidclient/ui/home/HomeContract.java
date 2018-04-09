package com.kdp.wanandroidclient.ui.home;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.ui.mvp.presenter.ICommonPresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/6
 */

public interface HomeContract{
    interface IHomePresenter extends ICommonPresenter {
        void getHomeList();
    }

    interface IHomeView extends IListDataView<ArticleBean>{

        void setBannerData(List<BannerBean> banner);

    }
}
