package com.kdp.wanandroidclient.ui.main;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.SearchModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;
import com.kdp.wanandroidclient.utils.LogUtils;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchPresenter extends BasePresenter<SearchContract.ISearchView> implements SearchContract.ISearchPresenter {
    private SearchModel mSearchModel;
    private SearchContract.ISearchView mSearchView;

    public SearchPresenter() {
        this.mSearchModel = new SearchModel();
    }

    @Override
    public void collectArticle() {
    }

    @Override
    public void collectInsideArticle() {
        mSearchView = getView();
        mSearchModel.collectInSideArticle(mSearchView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                mSearchView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSearchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }

    @Override
    public void unCollectArticle() {
        mSearchView = getView();
        mSearchModel.unCollectArticle(mSearchView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                mSearchView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSearchView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }

    @Override
    public void search() {
        mSearchView = getView();
        LogUtils.e(mSearchView.getPage()+"");
        mSearchModel.searchArticle(mSearchView.getPage(), mSearchView.getKeyword(), new RxPageListObserver<ArticleBean>(this) {
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
            }

        });
    }

    @Override
    public void getHotWord() {
        mSearchView = getView();
        mSearchModel.getHotWord(new RxObserver<List<HotwordBean>>(this) {
            @Override
            protected void onSuccess(List<HotwordBean> data) {
               mSearchView.setHotwordData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
            }
            @Override
            public void showLoading() {
            }
        });
    }

    @Override
    public void getFriend() {
        mSearchView = getView();
        mSearchModel.getFriend(new RxObserver<List<FriendBean>>(this) {
            @Override
            protected void onSuccess(List<FriendBean> data) {
                mSearchView.setFriendData(data);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
            }
            @Override
            public void showLoading() {
            }
        });
    }
}
