package com.kdp.wanandroidclient.ui.project;

import android.view.View;

import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

public class ProjectFragment extends BaseAbListFragment {
    @Override
    protected boolean isCanLoadMore() {
        return false;
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

    @Override
    protected void loadDatas() {

    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return null;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void setData(List data) {

    }

    @Override
    public int getArticleId() {
        return 0;
    }
}
