package com.kdp.wanandroidclient.net.callback;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.net.NetExceptionHandle;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.core.view.IView;

import io.reactivex.observers.DisposableObserver;

/**
 * RxRetrofit请求回调基类
 */

public abstract class RxBaseObserver<T> extends DisposableObserver<BaseBean<T>> {

    protected IView view;

    RxBaseObserver(BasePresenter mPresenter) {
        this.view = mPresenter.getView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //显示loading
        showLoading();
    }

    public void showLoading() {
        view.showLoading("");
    }

    @Override
    public void onError(Throwable e) {
        //隐藏loading
        hideLoading();
        //处理异常
        NetExceptionHandle.dealException(AppContext.getContext(),e);
    }

    @Override
    public void onComplete() {
        hideLoading();
    }

    private void hideLoading() {
        if (null != view)
            this.view.hideLoading();
    }

}
