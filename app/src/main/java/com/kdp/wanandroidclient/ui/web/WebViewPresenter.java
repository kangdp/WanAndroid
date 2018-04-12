package com.kdp.wanandroidclient.ui.web;

import com.kdp.wanandroidclient.ui.mvp.presenter.CommonPresenter;

/**
 * author: 康栋普
 * date: 2018/4/10
 */

public class WebViewPresenter extends CommonPresenter<WebViewContract.IWebView> implements WebViewContract.IWebViewPresenter {
    @Override
    public void collectArticle() {
        collectArticle(getView().getArticleId(), getView());
    }

    @Override
    public void collectInsideArticle() {
        collectInsideArticle(getView().getArticleId(), getView());
    }
}
