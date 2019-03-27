package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.TreeListModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

/**
 * 知识体系列表Presenter
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListPresenter extends BasePresenter<TreeListContract.ITreeListView> implements TreeListContract.ITreePresenter {

    private TreeListModel treeListModel;
    private TreeListContract.ITreeListView treeListView;

    TreeListPresenter() {
        treeListModel = new TreeListModel();
    }

    /**
     * 获取知识体系下的文章
     */
    @Override
    public void loadTreeList() {
        treeListView = getView();
        RxPageListObserver<Article> mTreeListRxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> mData) {
                treeListView.setData(mData);
                if (treeListView.getData().size() == 0)
                    treeListView.showEmpty();
                else
                    treeListView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                treeListView.showFail(errorMsg);
            }
        };
        treeListModel.getTreeList(treeListView.getPage(), treeListView.getCid(), mTreeListRxPageListObserver);
        addDisposable(mTreeListRxPageListObserver);

    }

    @Override
    public void collectArticle() {
        treeListView = getView();
        RxObserver<String> mCollectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                treeListView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        treeListModel.collectArticle(treeListView.getArticleId(), mCollectRxObserver);
        addDisposable(mCollectRxObserver);
    }

    @Override
    public void unCollectArticle() {
        treeListView = getView();
        RxObserver<String> unCollectRxObserver = new RxObserver<String>(this) {

            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                treeListView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }
        };
        treeListModel.unCollectArticle(treeListView.getArticleId(), unCollectRxObserver);
        addDisposable(unCollectRxObserver);
    }
}
