package com.kdp.wanandroidclient.ui.home;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.HomeModel;
import com.kdp.wanandroidclient.ui.core.presenter.CommonPresenter;

import java.util.List;

/**
 * Home Presenter
 * author: 康栋普
 * date: 2018/2/11
 */

public class HomePresenter extends CommonPresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    private HomeModel mHomeModel;
    private HomeContract.IHomeView homeView;

    HomePresenter() {
        this.mHomeModel = new HomeModel();
    }

    /**
     * 获取首页列表文章和Bannder
     */
    @Override
    public void getHomeList() {
        homeView = getView();
        RxPageListObserver<ArticleBean> mHomeRxPageListObserver = new RxPageListObserver<ArticleBean>(this) {

            @Override
            public void onSuccess(List<ArticleBean> mData) {
                homeView.setData(mData);
                if (homeView.getData().size() == 0)
                    homeView.showEmpty();
                else
                    homeView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                homeView.showFail(errorMsg);
            }
        };
        mHomeModel.getHomeData(homeView.getPage(), new RxConsumer<List<BannerBean>>() {
            @Override
            protected void onFail(String errorMsg) {
                homeView.showFail(errorMsg);
            }

            @Override
            protected void onSuccess(List<BannerBean> data) {
                homeView.setBannerData(data);
            }
        }, mHomeRxPageListObserver);

        addDisposable(mHomeRxPageListObserver);
    }


    @Override
    public void collectArticle() {
        collectArticle(homeView.getArticleId(),homeView);
    }

    @Override
    public void unCollectArticle() {
        unCollectArticle(homeView.getArticleId(),homeView);
    }
}
