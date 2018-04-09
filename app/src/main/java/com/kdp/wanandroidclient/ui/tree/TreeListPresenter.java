package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.TreeListModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListPresenter extends BasePresenter<TreeListContract.ITreeListView> implements TreeListContract.ITreePresenter {

    private TreeListModel mTreeListModel;

    private TreeListContract.ITreeListView mTreeListView;

    TreeListPresenter() {
        mTreeListModel = new TreeListModel();
    }

    @Override
    public void loadTreeList() {
        mTreeListView = getView();
        mTreeListModel.getTreeList(mTreeListView.getPage(), mTreeListView.getCid(), new RxPageListObserver<ArticleBean>(this,TreeListModel.class.getName()) {
            @Override
            public void onSuccess(List<ArticleBean> mData) {
                mTreeListView.setData(mData);
                if (mTreeListView.getData().size() == 0)
                    mTreeListView.showEmpty();
                else
                    mTreeListView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                mTreeListView.showFail(errorMsg);
            }
        });


    }

    @Override
    public void collectInsideArticle() {

    }

    @Override
    public void collectArticle() {

        mTreeListView = getView();

        mTreeListModel.collectArticle(mTreeListView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                mTreeListView.collect(true,AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mTreeListView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }

    @Override
    public void unCollectArticle() {
        mTreeListView = getView();

        mTreeListModel.unCollectArticle(mTreeListView.getArticleId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                mTreeListView.collect(false,AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mTreeListView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
            }
        });
    }
}
