package com.kdp.wanandroidclient.ui.base;

import android.content.Context;

import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IView;

/**
 * author: 康栋普
 * date: 2018/2/11
 */

public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends IView> extends BaseFragment{

    protected P mPresenter;


    protected abstract P createPresenter();

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView((V) this);
    }
}
