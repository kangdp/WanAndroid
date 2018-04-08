package com.kdp.wanandroidclient.ui.base;

import android.content.Intent;
import android.os.Bundle;

import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IView;
import com.kdp.wanandroidclient.utils.ToastUtils;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public abstract class BasePresenterActivity<P extends BasePresenter<V>, V extends IView> extends BaseActivity implements IView {

   protected P mPresenter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mPresenter = createPresenter();
        attachView();
    }

    @Override
    protected void onNavigationClick() {
        finish();
    }

    @Override
    protected void getIntent(Intent intent) {
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        //view置空
        detachView();
        //取消请求
        cancelRequest();
        super.onDestroy();
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void cancelRequest() {
        if (mPresenter != null) {
            mPresenter.cancelRequestTags();
        }
    }

    @Override
    protected boolean initToolbar() {
        return false;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void showLoading(String msg) {
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(this, msg);
    }


    @Override
    public void showError() {
    }

    @Override
    public void showEmpty() {

    }
}
