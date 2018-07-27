package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.ui.core.view.IListDataView;

/**
 * 知识体系列表协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface TreeListContract {

    interface ITreePresenter {

        void loadTreeList();

        void collectArticle();

        void unCollectArticle();
    }

    interface ITreeListView extends IListDataView<ArticleBean> {
        int getCid();
    }
}
