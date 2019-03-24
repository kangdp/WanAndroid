package com.kdp.wanandroidclient.ui.web;

import com.kdp.wanandroidclient.ui.core.view.IView;

/**
 * 文章详情页协约类
 * author: 康栋普
 * date: 2018/4/10
 */

public class WebViewContract {
    interface IWebViewPresenter{
        void collectArticle();
    }

    interface IWebView extends IView{
        int getArticleId();
        void collect(boolean isCollect,String result);
    }
}
