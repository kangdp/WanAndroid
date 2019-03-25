package com.kdp.wanandroidclient.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.ListDataHolder;

/**
 * author: 康栋普
 * date: 2018/2/9
 * 扩展:滑到底部自动加载
 */

public class LMRecyclerView extends RecyclerView {
    private OnFooterAutoLoadMoreListener listener;
    private boolean isCanLoadMore; //是否允许加载更多
    private boolean isReClickLoadMore;//是否可以点击重新加载
    private View mHeaderView;//头部
    private static final int VIEW_TYPE_NOMAL = 0;//nomal
    private static final int VIEW_TYPE_HEADER = 100;//header
    private static final int VIEW_TYPE_FOOTER = 200;//footer

    private int footerResId;

    private BaseAdapter mBaseAdapter;

    public LMRecyclerView(Context context) {
        this(context, null);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //添加监听
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 是否允许加载更多
     *
     * @param isCanLoadMore
     */
    public void setCanLoadMore(boolean isCanLoadMore) {
        this.isCanLoadMore = isCanLoadMore;
    }


    /**
     * 添加headerView
     *
     * @param header
     */
    public void addHeaderView(View header) {

        this.mHeaderView = header;
    }

    /**
     * 移除headerView
     */
    public void removeHeaderView() {
        this.mHeaderView = null;
    }

    /**
     * 显示底部加载状态
     *
     * @param footerResId
     */
    public void showFooterStatus(int footerResId) {
        this.footerResId = footerResId;
    }


    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        /**
         * 滑动状态监听
         * @param recyclerView
         * @param newState
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
        /**
         * 滑动监听
         * @param recyclerView
         * @param dx
         * @param dy
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayout = (LinearLayoutManager) layoutManager;
                int mLastChildPosition = linearLayout.findLastVisibleItemPosition();
                int itemTotalCount = linearLayout.getItemCount();
                View lastChildView = linearLayout.getChildAt(linearLayout.getChildCount() - 1);
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom = getBottom();
                if (mLastChildPosition == itemTotalCount - 1 && lastChildBottom == recyclerBottom) {
                    if (isCanLoadMore && listener != null) {
                        //业务代码
                        listener.loadMore();
                    }
                }
            }
        }
    };


    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mBaseAdapter = new BaseAdapter(adapter);
        }
        super.swapAdapter(mBaseAdapter, true);
    }

    private class BaseAdapter extends Adapter<ListDataHolder> {

        private Adapter mAdapter;

        public BaseAdapter(Adapter adapter) {
            this.mAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView != null && position == 0)
                return VIEW_TYPE_HEADER;
            if (isCanLoadMore && position == getItemCount() - 1)
                return VIEW_TYPE_FOOTER;
            return VIEW_TYPE_NOMAL;
        }

        @Override
        public ListDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_HEADER)
                return ListDataHolder.createViewHolder(mHeaderView);
            if (viewType == VIEW_TYPE_FOOTER)
                return ListDataHolder.createViewHolder(parent, R.layout.item_root_footer);
            return (ListDataHolder) mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ListDataHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == VIEW_TYPE_NOMAL) {
                if (mHeaderView != null) position--;
                mAdapter.onBindViewHolder(holder, position);
            } else if (viewType == VIEW_TYPE_FOOTER) {
                showFooterView(holder);
            } else if (viewType == VIEW_TYPE_HEADER) {
            }

        }

        @Override
        public int getItemCount() {
            int count = mAdapter.getItemCount();
            if (mHeaderView != null) count++;
            if (isCanLoadMore) count++;
            return count;
        }
    }

    /**
     * 显示footer
     *
     * @param holder
     */
    private void showFooterView(ListDataHolder holder) {
        FrameLayout rootView = holder.getView(R.id.root_footer);
        rootView.removeAllViews();
        if (footerResId != 0) {
            View footerView = LayoutInflater.from(getContext()).inflate(footerResId, rootView, false);
            rootView.addView(footerView);
            //只有加载更多失败,才能点击重新加载数据

            rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isReClickLoadMore) return;
                    if (listener != null) {
                        showLoadMore();
                        notifyDataSetChanged();
                        listener.reLoadMore();
                    }
                }
            });
        }
    }

    //显示底部加载更多
    public void showLoadMore() {
        showFooterStatus(R.layout.item_footer_loading_more);
        setIsReClickLoadMore(false);
    }

    //加载更多,没有更多的数据了
    public void showNoMoreData() {
        showFooterStatus(R.layout.item_footer_nomore);
        setIsReClickLoadMore(false);
    }

    //加载更多失败
    public void showLoadMoreError() {
        showFooterStatus(R.layout.item_footer_load_error);
        setIsReClickLoadMore(true);
    }

    /**
     * 底部是否可以重新加载更多
     *
     * @param isReClickLoadMore
     */
    public void setIsReClickLoadMore(boolean isReClickLoadMore) {
        this.isReClickLoadMore = isReClickLoadMore;
    }


    public void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }

    public void notifyItemChanged(int position) {
        getAdapter().notifyItemChanged(position);

    }

    public void notifyItemRemoved(int position) {
        getAdapter().notifyItemRemoved(position);
        getAdapter().notifyItemRangeChanged(position, getAdapter().getItemCount());
    }


    public void addFooterAutoLoadMoreListener(OnFooterAutoLoadMoreListener listener) {
        this.listener = listener;
    }

    public interface OnFooterAutoLoadMoreListener {
        //自动加载更多
        void loadMore();

        //加载出错,点击重新加载
        void reLoadMore();
    }


}
