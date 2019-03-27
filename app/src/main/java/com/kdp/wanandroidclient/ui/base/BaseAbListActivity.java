package com.kdp.wanandroidclient.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;
import com.kdp.wanandroidclient.utils.ViewAnimatorHelper;
import com.kdp.wanandroidclient.widget.StatusLayout;
import com.kdp.wanandroidclient.widget.LMRecyclerView;
import com.kdp.wanandroidclient.widget.NoAlphaItemAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 列表基类
 * Created by 康栋普 on 2018/2/1.
 */

public abstract class BaseAbListActivity<P extends BasePresenter, T> extends BasePresenterActivity<P> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IPageLoadDataView<T> {


    protected StatusLayout mStatusLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected LMRecyclerView mRecyclerView;
    private FloatingActionButton btn_scroll_top;
    protected BaseListAdapter<T> mListAdapter;
    private ViewAnimatorHelper viewAnimatorHelper;
    protected int page;
    protected int state = -1;
    protected boolean isAutoLoadMore = true;//是否显示自动加载
    protected List<T> mListData = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        btn_scroll_top.setVisibility(View.VISIBLE);
        btn_scroll_top.setOnClickListener(onScrollTopListener);
        viewAnimatorHelper = new ViewAnimatorHelper();
        viewAnimatorHelper.bindView(btn_scroll_top);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new NoAlphaItemAnimator());
        mRecyclerView.addOnScrollListener(onScrollListener);
        setCanLoadMore(isCanLoadMore());
        mRecyclerView.addFooterAutoLoadMoreListener(this);
        mListAdapter = getListAdapter();
        mStatusLayout.showLoding();
        if (mListAdapter != null) {
            mRecyclerView.addHeaderView(initHeaderView());
            mRecyclerView.setAdapter(mListAdapter);
            loadDatas();
        }
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mStatusLayout =  findViewById(R.id.containerLayout);
        mRecyclerView =  findViewById(R.id.recyclerView);
        btn_scroll_top = findViewById(R.id.btn_scroll_top);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0 && btn_scroll_top.getVisibility() != View.INVISIBLE && !viewAnimatorHelper.isAnimating()){
                viewAnimatorHelper.hideFloatActionButton();
            }else if (dy < 0 && btn_scroll_top.getVisibility() != View.VISIBLE){
                viewAnimatorHelper.showFloatActionButton();
            }
        }
    };

    private View.OnClickListener onScrollTopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    };
    /**
     * 加载Layout布局
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_list;
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
     * 开始自动加载更多
     */
    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }

    /**
     * 显示没有更多数据了
     */
    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }


    /**
     * 数据加载异常处理
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
     * 当前请求页
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 显示Loading
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (state == Const.PAGE_STATE.STATE_REFRESH)
            setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }

    /**
     * 是否能加载更多
     * @param isCanLoadMore
     */
    public void setCanLoadMore(boolean isCanLoadMore) {
        mRecyclerView.setCanLoadMore(isCanLoadMore);
    }

    /**
     * 是否禁用刷新
     * @param isEnableRefresh
     */
    public void setRefreshEnable(boolean isEnableRefresh) {
        mRefreshLayout.setEnabled(isEnableRefresh);
    }

    /**
     * 没有数据时显示
     */
    @Override
    public void showEmpty() {
        mStatusLayout.showEmpty();
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
     * 是否允许自动加载更多
     * @return
     */
    protected boolean isCanLoadMore(){
        return false;
    }

    protected View initHeaderView(){
        return null;
    }

    protected abstract void loadDatas();

    protected abstract BaseListAdapter<T>getListAdapter();
}
