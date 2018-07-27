package com.kdp.wanandroidclient.ui.core.presenter;

import com.kdp.wanandroidclient.ui.core.view.IView;

import io.reactivex.disposables.Disposable;

/**
 * Presenter接口类
 * Created by 康栋普 on 2018/2/1.
 */

public interface IPresenter<V extends IView> {

    //绑定View
    void attachView(V view);

    //解除View绑定
    void detachView();

    //检查View是否存在
    void checkAttachView();

    V getView();

    //添加指定的请求
    void addDisposable(Disposable disposable);
    //移除指定的请求
    void removeDisposable(Disposable disposable);
    //取消所有请求
    void removeAllDisposable();
}
