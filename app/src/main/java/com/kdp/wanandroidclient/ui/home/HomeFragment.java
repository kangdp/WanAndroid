package com.kdp.wanandroidclient.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.adapter.ArticleListAdapter;
import com.kdp.wanandroidclient.ui.adapter.BannerAdapter;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;
import com.kdp.wanandroidclient.ui.main.MainActivity;
import com.kdp.wanandroidclient.ui.tree.TreeActivity;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;
import com.kdp.wanandroidclient.utils.IntentUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.kdp.wanandroidclient.widget.BannerViewPager;
import java.util.ArrayList;
import java.util.List;


/**
 * 首页文章
 * author: 康栋普
 * date: 2018/2/12
 */

public class HomeFragment extends BaseAbListFragment<HomePresenter,Article> implements HomeContract.IHomeView, OnArticleListItemClickListener {
    private int id;//文章id
    private int position;
    private List<Banner> mBannerList = new ArrayList<>();
    private BannerViewPager mViewPager;
    private BannerAdapter mBannerAdapter;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    //初始化HeaderView
    @Override
    protected View initHeaderView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.main_header_banner, mRecyclerView, false);
        mViewPager = headerView.findViewById(R.id.viewPager);
        return headerView;
    }


    //设置Banner选中item
    private void setCurrentItem(final int position) {
        mViewPager.setCurrentItem(position, false);
    }

    //加载列表数据
    @Override
    protected void loadDatas() {
        if (page == getFirstPage()){
            //刷新
            mPresenter.getHomeData();
        }else {
            //加载更多
            mPresenter.getMoreArticleList();
        }
    }

    @Override
    protected BaseListAdapter<Article> getListAdapter() {
        return new ArticleListAdapter(this, Const.LIST_TYPE.HOME);
    }

    //Banner数据
    @Override
    public void setBannerData(List<Banner> banner) {
        mBannerList.clear();
        mBannerList.addAll(banner);
    }

    //列表数据
    @Override
    public void setData(List<Article> data) {
        mListData.addAll(data);
    }

    //显示内容
    @Override
    public void showContent() {
        notifyDatas();
        super.showContent();
    }

    //刷新所有数据
    public void notifyDatas() {
        if (mBannerAdapter == null) {
            mBannerAdapter = new BannerAdapter(mBannerList);
            mViewPager.setAdapter(mBannerAdapter);
            mViewPager.setOffscreenPageLimit(mBannerList.size());
            setCurrentItem(1000 * mBannerList.size());
        }
        mBannerAdapter.notifyDatas(mBannerList);
    }


    //收藏结果
    @Override
    public void collect(boolean isCollect, String result) {
        notifyItemData(isCollect, result);
    }

    //刷新单条Item
    private void notifyItemData(boolean isCollect, String result) {
        mListData.get(position).setCollect(isCollect);
        position++;
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
        ToastUtils.showToast(getActivity(), result);
    }

    //文章id
    @Override
    public int getArticleId() {
        return id;
    }

    //进入详情
    @Override
    public void onItemClick(int position,Article bean) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        bundle.putString(Const.BUNDLE_KEY.TYPE, Const.EVENT_ACTION.HOME);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDeleteCollectClick(int position, int id, int originId) {
    }

    //收藏click
    @Override
    public void onCollectClick(int position, int id) {
        if (!UserInfoManager.isLogin())
            IntentUtils.goLogin(getActivity());
        this.position = position;
        this.id = id;
        if (mListData.get(this.position).isCollect())
            mPresenter.unCollectArticle();
        else
            mPresenter.collectArticle();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stop();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mViewPager.stop();
        } else {
            mViewPager.start();
        }
    }

    @Override
    protected void receiveEvent(Object object) {
        Event mEvent = (Event) object;
        if (mEvent.type == Event.Type.REFRESH_ITEM) {
            Article bean = (Article) mEvent.object;
            for (int i = 0; i < mListData.size(); i++) {
                if (bean.equals(mListData.get(i))) {
                    position = i;
                    notifyItemData(bean.isCollect(), getString(R.string.collect_success));
                }
            }
        }else if (mEvent.type == Event.Type.SCROLL_TOP){
            mRecyclerView.smoothScrollToPosition(0);
        }else if (mEvent.type == Event.Type.REFRESH_LIST){
            refreshData();
        }
    }

    @Override
    protected String registerEvent() {
        return Const.EVENT_ACTION.HOME;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            RxEvent.getInstance().postEvent(Const.EVENT_ACTION.MAIN,new Event(Event.Type.SCALE,dy));
        }
    };
}
