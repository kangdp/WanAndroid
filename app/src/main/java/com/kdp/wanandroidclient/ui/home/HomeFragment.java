package com.kdp.wanandroidclient.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.adapter.ArticleListAdapter;
import com.kdp.wanandroidclient.ui.adapter.BannerAdapter;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;
import com.kdp.wanandroidclient.ui.logon.LogonActivity;
import com.kdp.wanandroidclient.ui.tree.TreeActivity;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.kdp.wanandroidclient.widget.BannerViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页文章
 * author: 康栋普
 * date: 2018/2/12
 */

public class HomeFragment extends BaseAbListFragment<HomePresenter, HomeContract.IHomeView, ArticleBean> implements HomeContract.IHomeView, OnArticleListItemClickListener {
    private int id;//文章id
    private int position;
    private List<BannerBean> mBannerList = new ArrayList<>();
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

    @Override
    protected View initHeaderView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.main_header_banner, mRecyclerView, false);
        mViewPager = (BannerViewPager) headerView.findViewById(R.id.viewPager);
        return headerView;
    }


    /**
     * setCurrentItem
     *
     * @param position
     */
    private void setCurrentItem(final int position) {
        mViewPager.setCurrentItem(position, false);
    }


    @Override
    protected void loadDatas() {
        mPresenter.getHomeList();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new ArticleListAdapter(this, Const.LIST_TYPE.HOME);
    }

    /**
     * 广告Banner
     *
     * @param banner
     */
    @Override
    public void setBannerData(List<BannerBean> banner) {
        mBannerList.clear();
        mBannerList.addAll(banner);
    }

    /**
     * 文章列表
     *
     * @param data
     */
    @Override
    public void setData(List<ArticleBean> data) {
        mListData.addAll(data);
    }

    /**
     * 显示内容
     */
    @Override
    public void showContent() {
        notifyDatas();
        super.showContent();
    }

    /**
     * 刷新数据
     */
    public void notifyDatas() {
        if (mBannerAdapter == null) {
            mBannerAdapter = new BannerAdapter(mBannerList);
            mViewPager.setAdapter(mBannerAdapter);
            //设置预加载两个页面
            mViewPager.setOffscreenPageLimit(2);
            setCurrentItem(1000 * mBannerList.size());
            mViewPager.start();
        }
        mBannerAdapter.notifyDatas(mBannerList);
    }


    /**
     * 收藏结果
     *
     * @param isCollect
     * @param result
     */
    @Override
    public void collect(boolean isCollect, String result) {
        notifyItemData(isCollect, result);
    }

    /**
     * 刷新item
     *
     * @param isCollect
     */
    private void notifyItemData(boolean isCollect, String result) {
        mListData.get(position).setCollect(isCollect);
        position++;
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
        ToastUtils.showToast(getActivity(), result);
    }

    /**
     * 文章id
     *
     * @return
     */
    @Override
    public int getArticleId() {
        return id;
    }

    /**
     * 进入详情
     *
     * @param title
     * @param url
     */
    @Override
    public void onItemClick(String title, String url) {

        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.TITLE, title);
        intent.putExtra(Const.BUNDLE_KEY.URL, url);
        startActivity(intent);
    }

    @Override
    public void onCollectClick(int position, int id, int originId) {
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.start();
    }

    /**
     * 收藏
     *
     * @param position
     * @param id
     */
    @Override
    public void onCollectClick(int position, int id) {
        if (!UserInfoManager.isLogin())
            startActivity(new Intent(getActivity(), LogonActivity.class));
        this.position = position;
        this.id = id;
        if (mListData.get(this.position).isCollect())
            mPresenter.unCollectArticle();
        else
            mPresenter.collectArticle();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onTreeClick(int chapterId, String chapterName) {
        Intent intent = new Intent(getActivity(), TreeActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.INTENT_ACTION_TYPE, Const.BUNDLE_KEY.INTENT_ACTION_LIST);
        intent.putExtra(Const.BUNDLE_KEY.CHAPTER_ID, chapterId);
        intent.putExtra(Const.BUNDLE_KEY.CHAPTER_NAME, chapterName);
        startActivity(intent);
    }
}
