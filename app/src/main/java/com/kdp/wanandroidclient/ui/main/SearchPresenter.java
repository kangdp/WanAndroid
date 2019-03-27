package com.kdp.wanandroidclient.ui.main;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Friend;
import com.kdp.wanandroidclient.bean.Hotword;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.SearchModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

/**
 * 搜索Presenter
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchPresenter extends BasePresenter<SearchContract.ISearchView> implements SearchContract.ISearchPresenter {
    private SearchModel searchModel;
    private SearchContract.ISearchView searchView;

    SearchPresenter() {
        this.searchModel = new SearchModel();
    }
    /**
     * 搜索文章
     */
    @Override
    public void search() {
        searchView = getView();
        RxPageListObserver<Article> searchRxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> mData) {
                searchView.setData(mData);
                if (searchView.getData().size() == 0)
                    searchView.showEmpty();
                else
                    searchView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                searchView.showFail(errorMsg);
            }
        };
        searchModel.searchArticle(searchView.getPage(), searchView.getKeyword(), searchRxPageListObserver);
        addDisposable(searchRxPageListObserver);
    }

    /**
     * 获取热搜
     */
    @Override
    public void getHotWord() {
        searchView = getView();
        RxObserver<List<Hotword>> hotWordRxObserver = new RxObserver<List<Hotword>>(this) {
            @Override
            protected void onSuccess(List<Hotword> data) {
                searchView.setHotwordData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                searchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        };
        searchModel.getHotWord(hotWordRxObserver);
        addDisposable(hotWordRxObserver);
    }

    /**
     * 获取常用网站
     */
    @Override
    public void getFriend() {
        RxObserver<List<Friend>> friendRxObserver = new RxObserver<List<Friend>>(this) {
            @Override
            protected void onSuccess(List<Friend> data) {
                searchView.setFriendData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                searchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        };
        searchModel.getFriend(friendRxObserver);
        addDisposable(friendRxObserver);
    }

    @Override
    public void collectArticle() {
        RxObserver<String> collectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                searchView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        searchModel.collectArticle(searchView.getArticleId(), collectRxObserver);
        addDisposable(collectRxObserver);
    }

    @Override
    public void unCollectArticle() {
        RxObserver<String> unCollectRxObserver = new RxObserver<String>(this) {

            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                searchView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }
        };
        searchModel.unCollectArticle(searchView.getArticleId(), unCollectRxObserver);
        addDisposable(unCollectRxObserver);
    }
}
