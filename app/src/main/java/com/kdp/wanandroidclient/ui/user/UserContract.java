package com.kdp.wanandroidclient.ui.user;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public interface UserContract {
    interface IUserPresenter {
        void loadCollectList();
        void deleteCollectArticle();
    }

    interface IUserView extends IListDataView<ArticleBean> {
        int getOriginId();

        void deleteCollect();
    }
}
