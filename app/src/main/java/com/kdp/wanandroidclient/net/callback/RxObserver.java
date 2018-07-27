package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.net.NetConfig;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

/**
 * RxRetrofit通用接口回调类
 */

public abstract class RxObserver<T> extends RxBaseObserver<T> {
    public RxObserver(BasePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void onNext(BaseBean<T> mBaseBean) {

        //请求成功
        if (mBaseBean.errorCode == NetConfig.REQUEST_SUCCESS) {
            onSuccess(mBaseBean.data);
        } else {
         //失败
            onFail(mBaseBean.errorCode, mBaseBean.errorMsg);
        }
    }

    protected abstract void onSuccess(T data);

    protected abstract void onFail(int errorCode, String errorMsg);

}
