package com.kdp.wanandroidclient.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.kdp.wanandroidclient.widget.StatusLayout;
import com.kdp.wanandroidclient.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment列表基类
 * author: 康栋普
 * date: 2018/2/11
 */

public abstract class BaseAbListFragment<P extends BasePresenter, T> extends BasePresenterFragment<P> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IPageLoadDataView<T> {

    protected StatusLayout mStatusLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected LMRecyclerView mRecyclerView;
    protected BaseListAdapter<T> mListAdapter;
    protected int page = getFirstPage();
    protected int state = -1;
    protected boolean isAutoLoadMore = true;//是否开启自动加载
    private boolean isPreload; //是否已经预加载完成
    private boolean isVisible; //是否可见
    private boolean isFirst = true;//是否第一次加载数据
    private boolean isEnableLazy = false; //是否开启懒加载
    protected List<T> mListData = new ArrayList<>();


    @Override
    protected void initViews(View view) {
        mRefreshLayout =  view.findViewById(R.id.refreshLayout);
        mStatusLayout =  view.findViewById(R.id.containerLayout);
        mRecyclerView =  view.findViewById(R.id.recyclerView);
    }


    @Override
    public List<T> getData() {
        return mListData;
    }

    /**
     * 请求数据成功展示内容
     */
    @Override
    public void showContent() {
        mStatusLayout.showContent();
        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_list;
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
                mStatusLayout.showLoding();
                loadDatas();
            }
        }
    }


    private void lazyLoad() {
        if (!isPreload || !isVisible || !isFirst) {
            return;
        }
        mStatusLayout.showLoding();
        loadDatas();
        isFirst = false;
    }

    /**
     * 懒加载处理
     *
     * @param isVisibleToUser
     */
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

    /**
     * 下拉刷新监听
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };


    /**
     * 刷新数据，回到第一页
     */
    public void refreshData() {
        state = Const.PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = getFirstPage();
        loadDatas();
    }

    /**
     * 滑到底部开始加载更多数据
     */
    @Override
    public void loadMore() {
        if (!isAutoLoadMore) return;
        state = Const.PAGE_STATE.STATE_LOAD_MORE;
        loadDatas();
    }

    /**
     * 底部加载更多失败，点击重新加载
     */
    @Override
    public void reLoadMore() {
        isAutoLoadMore = true;
        loadMore();
    }


    /**
     * 清空当前列表数据
     */
    @Override
    public void clearListData() {
        mListData.clear();
    }

    /**
     * 自动加载更多
     */
    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }


    /**
     * 底部加载没有更多数据时显示
     */
    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }


    /**
     * 数据加载异常时显示
     */
    @Override
    public void showError() {
        isAutoLoadMore = false;
        //如果是加载更多出现异常，那么底部就显示点击重新加载;
        // 否则，就清空数据，显示没有数据
        if (state == Const.PAGE_STATE.STATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            mListAdapter.notifyAllDatas(mListData, mRecyclerView);
        } else {
            mStatusLayout.showError();
        }

    }

    /**
     * 没有数据时显示
     */
    @Override
    public void showEmpty() {
        mStatusLayout.showEmpty();
    }


    /**
     * 当前请求页
     *
     * @return
     */
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
    public int getFirstPage() {
        return 0;
    }

    /**
     * 是否开启懒加载
     * @return
     */
    protected boolean isEnableLazy() {
        return false;
    }
    /**
     * 是否允许自动加载更多
     * @return
     */
    protected boolean isCanLoadMore(){
        return false;
    }
    @Override
    protected void getBundle(Bundle bundle) {
    }

    protected View initHeaderView(){
        return null;
    }

    protected abstract void loadDatas();

    protected abstract BaseListAdapter<T> getListAdapter();

}
