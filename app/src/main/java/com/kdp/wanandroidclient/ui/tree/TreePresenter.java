package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.TreeModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import java.util.List;

/**
 * 知识体系
 * author: 康栋普
 * date: 2018/2/26
 */

public class TreePresenter extends BasePresenter<TreeContract.ITreeView> implements TreeContract.ITreePresenter {

    private TreeModel mTreeModel;
    private TreeContract.ITreeView mSystemView;

    TreePresenter() {
        mTreeModel = new TreeModel();
    }

    /**
     * 获取知识体系下的分类
     */
    @Override
    public void loadTree() {
        mSystemView = getView();
        RxObserver<List<Tree>> mTreeRxObserver = new RxObserver<List<Tree>>(this) {
            @Override
            protected void onSuccess(List<Tree> data) {
                mSystemView.setData(data);
                if (mSystemView.getData().size() == 0) {
                    mSystemView.showEmpty();
                } else {
                    mSystemView.showContent();
                }
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSystemView.showFail(errorMsg);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSystemView.showError();
            }
        };
        mTreeModel.getTree(mTreeRxObserver);
        addDisposable(mTreeRxObserver);
    }
}
