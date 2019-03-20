package com.kdp.wanandroidclient.ui.home;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.net.callback.RxConsumer;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.CommonModel;
import com.kdp.wanandroidclient.ui.core.model.impl.HomeModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;


import java.util.List;

/**
 * Home Presenter
 * author: 康栋普
 * date: 2018/2/11
 */

public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    private HomeModel homeModel;
    private HomeContract.IHomeView homeView;

    HomePresenter() {
        this.homeModel = new HomeModel();
    }

    /**
     * 获取首页列表文章和Bannder
     */
    @Override
    public void getHomeList() {
        homeView = getView();
        RxPageListObserver<Article> mHomeRxPageListObserver = new RxPageListObserver<Article>(this) {

            @Override
            public void onSuccess(List<Article> mData) {
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
        homeModel.getHomeData(homeView.getPage(), new RxConsumer<List<Banner>>() {
            @Override
            protected void onFail(String errorMsg) {
                homeView.showFail(errorMsg);
            }

            @Override
            protected void onSuccess(List<Banner> data) {
                homeView.setBannerData(data);
            }
        }, mHomeRxPageListObserver);

        addDisposable(mHomeRxPageListObserver);
    }


    @Override
    public void collectArticle() {
        RxObserver<String> mCollectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                homeView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        homeModel.collectArticle(homeView.getArticleId(), mCollectRxObserver);
        addDisposable(mCollectRxObserver);
    }

    @Override
    public void unCollectArticle() {
        RxObserver<String> unCollectRxObserver = new RxObserver<String>(this) {

            @Override
            protected void onStart() {
            }

            @Override
            protected void onSuccess(String data) {
                homeView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }
        };
        homeModel.unCollectArticle(homeView.getArticleId(), unCollectRxObserver);
        addDisposable(unCollectRxObserver);
    }

}
