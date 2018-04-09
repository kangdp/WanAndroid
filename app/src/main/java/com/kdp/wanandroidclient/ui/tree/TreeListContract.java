package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.ui.mvp.presenter.ICommonPresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;

/**
 * author: 康栋普
 * date: 2018/3/6
 */

public interface TreeListContract {

    interface ITreePresenter extends ICommonPresenter {

        void loadTreeList();
    }

    interface ITreeListView extends IListDataView<ArticleBean> {
        int getCid();
    }
}
