package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

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

    interface ITreeListView extends IPageLoadDataView<Article> {
        int getCid();
        int getArticleId();//文章id
        void collect(boolean isCollect,String result);
    }
}
