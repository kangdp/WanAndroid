package com.kdp.wanandroidclient.ui.web;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.CommonModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

/**
 * 文章详情页Presenter
 * author: 康栋普
 * date: 2018/4/10
 */

public class WebViewPresenter extends BasePresenter<WebViewContract.IWebView> implements WebViewContract.IWebViewPresenter {
    private CommonModel commonModel;
    private WebViewContract.IWebView webView;

    WebViewPresenter() {
        this.commonModel = new CommonModel();
    }

    @Override
    public void collectArticle() {
        webView = getView();
        RxObserver<String> mCollectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                webView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        commonModel.collectArticle(webView.getArticleId(), mCollectRxObserver);
        addDisposable(mCollectRxObserver);
    }

}
