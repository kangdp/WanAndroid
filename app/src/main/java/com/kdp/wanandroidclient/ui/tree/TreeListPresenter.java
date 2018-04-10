package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.TreeListModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.CommonPresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListPresenter extends CommonPresenter<TreeListContract.ITreeListView> implements TreeListContract.ITreePresenter {

    private TreeListModel mTreeListModel;

    private TreeListContract.ITreeListView mTreeListView;

    TreeListPresenter() {
        mTreeListModel = new TreeListModel();
    }

    @Override
    public void loadTreeList() {
        mTreeListView = getView();
        RxPageListObserver<ArticleBean> mTreeListRxPageListObserver = new RxPageListObserver<ArticleBean>(this) {
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
        };
        mTreeListModel.getTreeList(mTreeListView.getPage(), mTreeListView.getCid(), mTreeListRxPageListObserver);
        addDisposable(mTreeListRxPageListObserver);

    }

    @Override
    public void collectArticle() {
        collectArticle(mTreeListView.getArticleId(),mTreeListView);
    }

    @Override
    public void unCollectArticle() {
        unCollectArticle(mTreeListView.getArticleId(),mTreeListView);
    }
}
