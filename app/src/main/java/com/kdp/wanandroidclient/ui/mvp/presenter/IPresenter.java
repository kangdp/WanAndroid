package com.kdp.wanandroidclient.ui.mvp.presenter;

import com.kdp.wanandroidclient.ui.mvp.view.IView;

import io.reactivex.disposables.Disposable;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public interface IPresenter<V extends IView> {

    /**
     * bind view
     *
     * @param view
     */
    void attachView(V view);

    /**
     * unbind view
     */
    void detachView();

    /**
     * check view
     */
    void checkAttachView();

    V getView();


    void addRequestTag(String tag,Disposable disposable);

    /**
     * cancel request
     */
    void cancelRequestTags();

}
