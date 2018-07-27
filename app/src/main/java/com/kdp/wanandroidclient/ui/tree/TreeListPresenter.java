package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.TreeListModel;
import com.kdp.wanandroidclient.ui.core.presenter.CommonPresenter;

import java.util.List;

/**
 * 知识体系列表Presenter
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListPresenter extends CommonPresenter<TreeListContract.ITreeListView> implements TreeListContract.ITreePresenter {

    private TreeListModel mTreeListModel;

    private TreeListContract.ITreeListView mTreeListView;

    TreeListPresenter() {
        mTreeListModel = new TreeListModel();
    }

    /**
     * 获取知识体系下的文章
     */
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
