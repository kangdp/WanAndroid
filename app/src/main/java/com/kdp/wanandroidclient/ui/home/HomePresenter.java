package com.kdp.wanandroidclient.ui.home;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.CollectModel;
import com.kdp.wanandroidclient.ui.mvp.model.impl.HomeModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/2/11
 */

public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    private HomeModel mHomeModel;
    private CollectModel mCollectArticleModel;
    private HomeContract.IHomeView homeView;

    public HomePresenter() {
        this.mHomeModel = new HomeModel();
        this.mCollectArticleModel = new CollectModel();
    }

    @Override
    public void getHomeList() {
        homeView = getView();
        final int page = homeView.getPage();
        mHomeModel.getHomeData(page, new RxConsumer<List<BannerBean>>() {
            @Override
            protected void onFail(String errorMsg) {
                homeView.showFail(errorMsg);
            }

            @Override
            protected void onSuccess(List<BannerBean> data) {
                homeView.setBannerData(data);
            }

        }, new RxPageListObserver<ArticleBean>(this,HomeModel.class.getName()) {
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
        });


    }

    @Override
    public void collectArticle() {
        homeView = getView();
        mCollectArticleModel.collectArticle(homeView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                homeView.collect(true,AppContext.getContext().getString(R.string.collect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                homeView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }

    @Override
    public void collectInsideArticle() {

    }

    @Override
    public void unCollectArticle() {
        homeView = getView();
        mCollectArticleModel.unCollectArticle(homeView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                homeView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                homeView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }

}
