package com.kdp.wanandroidclient.net.callback;

import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.PageListDataBean;
import com.kdp.wanandroidclient.net.NetConfig;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * 分页列表加载通用Observer
 * author: 康栋普
 * date: 2018/3/13
 */

public abstract class RxPageListObserver<T> extends RxBaseObserver<PageListDataBean<T>> {

    private IListDataView<T> mListDataView;

    protected RxPageListObserver(BasePresenter mPresenter,String mTag) {
        super(mPresenter,mTag);
        this.mListDataView = (IListDataView<T>) mPresenter.getView();
    }

    public RxPageListObserver(BasePresenter mPresenter) {
        this(mPresenter,null);
    }

    @Override
    public void onNext(BaseBean<PageListDataBean<T>> baseBean) {
        if (baseBean.errorCode == NetConfig.REQUEST_SUCCESS) {

            PageListDataBean<T> mListData = baseBean.data;

            if (mListDataView.getPage() == 0) {
                mListDataView.refresh();
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
