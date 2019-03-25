package com.kdp.wanandroidclient.ui.home;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.HomeData;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.net.callback.RxZipObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.HomeModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import java.util.List;
import io.reactivex.functions.Function3;

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
     * 获取首页Banner、置顶文章、列表文章
     */
    @Override
    public void getHomeData() {
        homeView = getView();
        Function3<BaseBean<List<Banner>>, BaseBean<List<Article>>, BaseBean<PageListData<Article>>, HomeData> function3 = new Function3<BaseBean<List<Banner>>, BaseBean<List<Article>>, BaseBean<PageListData<Article>>, HomeData>() {
            @Override
            public HomeData apply(BaseBean<List<Banner>> banner, BaseBean<List<Article>> homeTop, BaseBean<PageListData<Article>> home) throws Exception {
                HomeData homeData = new HomeData();
                homeData.setBanner(banner);
                for (Article bean : homeTop.data){
                    //置顶
                    bean.setTop(true);
                }
                homeData.setHomeTop(homeTop);
                homeData.setHome(home);
                return homeData;
            }
        };


        RxZipObserver<HomeData> rxZipObserver = new RxZipObserver<HomeData>(this) {
            @Override
            public void onNext(HomeData homeData) {
                homeView.setBannerData(homeData.getBanner().data);
                List<Article> list = homeData.getHome().data.getDatas();
                list.addAll(0,homeData.getHomeTop().data);
                homeView.clearListData();
                homeView.autoLoadMore();
                homeView.setData(list);
                if (homeView.getData().size() == 0) {
                    homeView.showEmpty();
                }else {
                    homeView.showContent();
                }
            }
        };
        homeModel.getHomeData(homeView.getPage(),function3, rxZipObserver);
        addDisposable(rxZipObserver);
    }

    /**
     * 加载更多，获取更多文章
     */
    @Override
    public void getMoreArticleList() {
        homeView = getView();
        RxPageListObserver<Article> rxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> homeList) {
                homeView.getData().addAll(homeList);
                homeView.showContent();
            }
            @Override
            public void onFail(int errorCode, String errorMsg) {
                homeView.showFail(errorMsg);
            }
        };
        homeModel.getMoreArticleList(homeView.getPage(),rxPageListObserver);
        addDisposable(rxPageListObserver);
    }

    /**
     * 收藏
     */
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

    /**
     * 取消收藏
     */
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
