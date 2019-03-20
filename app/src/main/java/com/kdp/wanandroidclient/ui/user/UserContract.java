package com.kdp.wanandroidclient.ui.user;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

/**
 * 用户协约类
 * author: 康栋普
 * date: 2018/3/21
 */

public interface UserContract {
    interface IUserPresenter {
        void loadCollectList();
        void deleteCollectArticle();
    }

    interface IUserView extends IPageLoadDataView<Article> {
        int getOriginId();
        int getArticleId();//文章id
        void deleteCollect();
    }
}
