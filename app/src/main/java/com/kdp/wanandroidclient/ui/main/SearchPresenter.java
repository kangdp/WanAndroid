package com.kdp.wanandroidclient.ui.main;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.SearchModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.CommonPresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchPresenter extends CommonPresenter<SearchContract.ISearchView> implements SearchContract.ISearchPresenter {
    private SearchModel mSearchModel;
    private SearchContract.ISearchView mSearchView;

    public SearchPresenter() {
        this.mSearchModel = new SearchModel();
    }

    @Override
    public void search() {
        mSearchView = getView();
        RxPageListObserver<ArticleBean> mSearchRxPageListObserver = new RxPageListObserver<ArticleBean>(this) {
            @Override
            public void onSuccess(List<ArticleBean> mData) {
                mSearchView.setData(mData);
                if (mSearchView.getData().size() == 0)
                    mSearchView.showEmpty();
                else
                    mSearchView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                mSearchView.showFail(errorMsg);
            }
        };
        mSearchModel.searchArticle(mSearchView.getPage(), mSearchView.getKeyword(), mSearchRxPageListObserver);
        addDisposable(mSearchRxPageListObserver);
    }

    @Override
    public void getHotWord() {
        mSearchView = getView();
        RxObserver<List<HotwordBean>> mHotWordRxObserver = new RxObserver<List<HotwordBean>>(this) {
            @Override
            protected void onSuccess(List<HotwordBean> data) {
                mSearchView.setHotwordData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSearchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        };
        mSearchModel.getHotWord(mHotWordRxObserver);
        addDisposable(mHotWordRxObserver);
    }

    @Override
    public void getFriend() {
        RxObserver<List<FriendBean>> mFriendRxObserver = new RxObserver<List<FriendBean>>(this) {
            @Override
            protected void onSuccess(List<FriendBean> data) {
                mSearchView.setFriendData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSearchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        };
        mSearchModel.getFriend(mFriendRxObserver);
        addDisposable(mFriendRxObserver);
    }

    @Override
    public void collectInsideArticle() {
        collectInsideArticle(mSearchView.getArticleId(),mSearchView);
    }

    @Override
    public void unCollectArticle() {
        unCollectArticle(mSearchView.getArticleId(),mSearchView);
    }
}
