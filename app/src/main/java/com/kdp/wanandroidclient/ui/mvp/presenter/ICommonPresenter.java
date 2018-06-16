package com.kdp.wanandroidclient.ui.mvp.presenter;

import com.kdp.wanandroidclient.ui.mvp.view.IView;

/**
 * 通用业务接口
 * author: 康栋普
 * date: 2018/3/20
 */

public interface ICommonPresenter {

    void collectArticle(int articleId,IView view);

    void collectInsideArticle(int articleId,IView view);

    void unCollectArticle(int articleId,IView view);

}
