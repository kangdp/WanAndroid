package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.net.NetConfig;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

import java.util.List;

/**
 * 分页加载功能的接口回调类
 * 分页加载逻辑在这里统一处理
 * author: 康栋普
 * date: 2018/3/13
 */

public abstract class RxPageListObserver<T> extends RxBaseObserver<PageListData<T>> {

    private IPageLoadDataView mListDataView;

    public RxPageListObserver(BasePresenter mPresenter) {
        super(mPresenter);
        this.mListDataView = (IPageLoadDataView) mPresenter.getView();
    }
    @Override
    public void onNext(BaseBean<PageListData<T>> baseBean) {
        if (baseBean.errorCode == NetConfig.REQUEST_SUCCESS) {

            PageListData<T> mListData = baseBean.data;
            if (mListDataView.getPage() == mListDataView.getFirstPage()) {
                mListDataView.clearListData();
            }
            if (mListData.isOver()) {
                mListDataView.showNoMore();
            } else {
                mListDataView.autoLoadMore();
            }
            onSuccess(mListData.getDatas());
        } else {
            onFail(baseBean.errorCode, baseBean.errorMsg);
        }
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        mListDataView.showError();
    }

    public abstract void onSuccess(List<T> mData);

    public abstract void onFail(int errorCode, String errorMsg);
}
