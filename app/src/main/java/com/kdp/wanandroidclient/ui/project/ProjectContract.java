package com.kdp.wanandroidclient.ui.project;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

public interface ProjectContract {
    interface IProjectPresenter {
        void getProjectList();
        void collectArticle();
        void unCollectArticle();
    }

    interface IProjectView extends IPageLoadDataView<Article>{
        int getCid();
        int getArticleId();
        void collect(boolean isCollect,String result);
    }
}
