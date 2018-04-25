package com.kdp.wanandroidclient.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;
import com.kdp.wanandroidclient.ui.mvp.view.IView;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.kdp.wanandroidclient.widget.ContainerLayout;
import com.kdp.wanandroidclient.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表fragment基类
 * author: 康栋普
 * date: 2018/2/11
 */

public abstract class BaseAbListFragment<P extends BasePresenter<V>, V extends IView, T> extends BasePresenterFragment<P, V> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IListDataView<T> {

    protected ContainerLayout mContainerLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected LMRecyclerView mRecyclerView;
    protected BaseListAdapter mListAdapter;
    protected int page;
    protected int state;
    protected boolean isAutoLoadMore = true;//是否开启自动加载
    private boolean isPreload; //是否已经预加载完成
    private boolean isVisible; //是否可见
    private boolean isFirst = true;//是否第一次加载数据
    private boolean isEnableLazy = false; //是否懒加载
    protected List<T> mListData = new ArrayList<>();


    @Override
    protected void initViews(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mContainerLayout = (ContainerLayout) view.findViewById(R.id.containerLayout);
        mRecyclerView = (LMRecyclerView) view.findViewById(R.id.recyclerView);
    }


    @Override
    public List<T> getData() {
        return mListData;
    }

    //显示内容
    @Override
    public void showContent() {
        mContainerLayout.showContent();
        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
    }

    @Override
    public void collect(boolean isCollect, String result) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_list;
    }

    @Override
    protected void getBundle(Bundle bundle) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setCanLoadMore(isCanLoadMore());
        mRecyclerView.addFooterAutoLoadMoreListener(this);
        mListAdapter = getListAdapter();
        if (mListAdapter != null) {
            mRecyclerView.addHeaderView(initHeaderView());
            mRecyclerView.setAdapter(mListAdapter);
            if (isEnableLazy) {
                isPreload = true;
                isFirst = true;
                lazyLoad();
            } else {
                loadDatas();
            }
        }
    }

    //是否开启懒加载
    protected boolean isEnableLazy() {
        return false;
    }

    private void lazyLoad() {
        if (!isPreload || !isVisible || !isFirst) {
            return;
        }
        loadDatas();
        isFirst = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isEnableLazy = isEnableLazy();//默认不开启懒加载
        if (!isEnableLazy) return;
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    // 是否能够自动加载更多
    protected abstract boolean isCanLoadMore();

    //下拉刷新监听
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };


    // 刷新列表
    public void refreshData() {
        state = Const.PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
    }

    //滑到底部自动加载
    @Override
    public void loadMore() {
        if (!isAutoLoadMore) return;
        state = Const.PAGE_STATE.STATE_LOAD_MORE;
        loadDatas();
    }

    //点击重新加载
    @Override
    public void reLoadMore() {
        isAutoLoadMore = true;
        loadMore();
    }


    //清空列表数据
    @Override
    public void clearListData() {
        mListData.clear();
    }

    //自动加载更多
    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }

    //没有更多数据
    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }


    //加载出错
    @Override
    public void showError() {
        isAutoLoadMore = false;
        //如果是加载更多出错，那么底部就显示点击重新加载;
        // 否则，就清空数据，显示没有数据
        if (state == Const.PAGE_STATE.STATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            mListAdapter.notifyAllDatas(mListData, mRecyclerView);
        } else {
            mContainerLayout.showError();
        }

    }

    //无数据
    @Override
    public void showEmpty() {
        mContainerLayout.showEmpty();
    }


    //当前请求的页
    public int getPage() {
        return page;
    }

    @Override
    public void showLoading(String msg) {
        if (state == Const.PAGE_STATE.STATE_REFRESH)
            setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }


    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    protected void setRefreshing(final boolean isRefrshing) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(isRefrshing);
            }
        }, 100);

    }

    @Override
    protected void receiveEvent(Object object) {
    }

    @Override
    protected String registerEvent() {
        return null;
    }

    protected abstract View initHeaderView();

    protected abstract void loadDatas();

    protected abstract BaseListAdapter getListAdapter();
}
