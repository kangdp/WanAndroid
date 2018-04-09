package com.kdp.wanandroidclient.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.adapter.ArticleListAdapter;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListActivity;
import com.kdp.wanandroidclient.ui.logon.LogonActivity;
import com.kdp.wanandroidclient.ui.tree.TreeActivity;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 搜索页面
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchActivity extends BaseAbListActivity<SearchPresenter, SearchContract.ISearchView, ArticleBean> implements SearchContract.ISearchView, OnArticleListItemClickListener {
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private View mHeaderView;
    private TagFlowLayout mKeywordTagLayout, mFriendTagLayout;
    private int position;
    private int id;
    private String keyword = "";
    private List<HotwordBean> mHotwordDatas = new ArrayList<>();
    private List<FriendBean> mFriendDatas = new ArrayList<>();

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.search);
        return true;
    }

    @Override
    protected View initHeaderView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.search_header, mRecyclerView, false);
        mKeywordTagLayout = (TagFlowLayout) mHeaderView.findViewById(R.id.keywordTaglayout);
        mFriendTagLayout = (TagFlowLayout) mHeaderView.findViewById(R.id.friendTaglayout);
        return mHeaderView;
    }

    //设置搜索的数据
    @Override
    public void setData(List<ArticleBean> data) {
        mRecyclerView.removeHeaderView();
        setRefreshEnable(true);
        setCanLoadMore(true);
        if (state != Const.PAGE_STATE.STATE_LOAD_MORE)
            mRecyclerView.scrollToPosition(0);
        mListData.addAll(data);
    }

    @Override
    public void showError() {
        super.showError();
        setRefreshEnable(true);
    }


    @Override
    public String getKeyword() {
        return keyword;
    }


    @Override
    public int getArticleId() {
        return id;
    }

    //热搜关键词
    @Override
    public void setHotwordData(final List<HotwordBean> mHotListDatas) {
        mHotwordDatas.clear();
        mHotwordDatas.addAll(mHotListDatas);
        mKeywordTagLayout.setAdapter(new TagAdapter<HotwordBean>(mHotListDatas) {
            @Override
            public View getView(FlowLayout parent, int position, HotwordBean hotwordBean) {
                TextView tagView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_tag, mKeywordTagLayout, false);
                tagView.setText(hotwordBean.getName());
                setTagTextColor(tagView);
                return tagView;
            }
        });
        mKeywordTagLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                keyword = mHotListDatas.get(position).getName();
                searchAutoComplete.setText(keyword);
                searchAutoComplete.setSelection(keyword.length());
                mSearchView.setQuery(keyword, true);
                return false;
            }
        });
    }

    //历史网站
    @Override
    public void setFriendData(final List<FriendBean> mFriendListDatas) {
        mFriendDatas.clear();
        mFriendDatas.addAll(mFriendListDatas);
        mFriendTagLayout.setAdapter(new TagAdapter<FriendBean>(mFriendDatas) {
            @Override
            public View getView(FlowLayout parent, int position, FriendBean friendBean) {
                TextView tagView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_tag, mFriendTagLayout, false);
                tagView.setText(friendBean.getName());
                setTagTextColor(tagView);
                return tagView;
            }
        });
        mFriendTagLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                FriendBean mFriendBean = mFriendListDatas.get(position);
                Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                intent.putExtra(Const.BUNDLE_KEY.TITLE, mFriendBean.getName());
                intent.putExtra(Const.BUNDLE_KEY.URL, mFriendBean.getLink());
                startActivity(intent);
                return false;
            }
        });
    }

    //关键词颜色
    private void setTagTextColor(TextView tagView) {
        int red, green, blue;
        Random mRandow = new Random();
        red = mRandow.nextInt(255);
        green = mRandow.nextInt(255);
        blue = mRandow.nextInt(255);
        int color = Color.rgb(red, green, blue);
        tagView.setTextColor(color);
    }

    //SearchView相关设置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_setting, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        //获取搜索框
        mSearchView = (SearchView) menuItem.getActionView();
        //设置搜索hint
        mSearchView.setQueryHint(getString(R.string.search_keyword));
        mSearchView.onActionViewExpanded();
        //去除搜索框背景
        deleteSearchPlate();
        searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color._60ffffff));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView mCloseView = (ImageView) mSearchView.findViewById(R.id.search_close_btn);
            mCloseView.setBackground(ContextCompat.getDrawable(this, R.drawable.ripple_close));
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                refreshData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    keyword = newText;
                    if (mHotwordDatas.size() == 0)
                        loadTagDatas();
                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

   //去除SearchView的输入框默认背景
    private void deleteSearchPlate() {
        try {
            Class<?> cla = mSearchView.getClass();
            Field mSearchPlateField = cla.getDeclaredField("mSearchPlate");
            mSearchPlateField.setAccessible(true);
            View searchPlate = (View) mSearchPlateField.get(mSearchView);
            searchPlate.setBackground(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected void loadDatas() {
        if (mSearchView == null) {
            loadTagDatas();
            return;
        }

        loadArticleListDatas();
    }

    private void loadTagDatas() {
        setRefreshEnable(false);
        setCanLoadMore(false);
        mListData.clear();
        mRecyclerView.addHeaderView(mHeaderView);
        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
        showContent();
        mPresenter.getHotWord();
        mPresenter.getFriend();
    }

    private void loadArticleListDatas() {
        mHotwordDatas.clear();
        mFriendDatas.clear();
        mPresenter.search();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new ArticleListAdapter(this, Const.LIST_TYPE.SEARCH);
    }

    @Override
    public void onItemClick(String title, String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.TITLE, title);
        intent.putExtra(Const.BUNDLE_KEY.URL, url);
        startActivity(intent);
    }

    @Override
    public void onCollectClick(int position, int id, int originId) {
    }

    @Override
    public void collect(boolean isCollect, String result) {
        notifyItemData(isCollect, result);
    }

    private void notifyItemData(boolean isCollect, String result) {
        mListData.get(position).setCollect(isCollect);
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
        ToastUtils.showToast(this, result);
    }

    @Override
    public void onCollectClick(int position, int id) {
        if (!UserInfoManager.isLogin())
            startActivity(new Intent(this, LogonActivity.class));
        this.position = position;
        this.id = id;
        if (mListData.get(this.position).isCollect())
            mPresenter.unCollectArticle();
        else
            mPresenter.collectInsideArticle();
    }

    @Override
    public void onTreeClick(int chapterId, String chapterName) {
        Intent intent = new Intent(this, TreeActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.INTENT_ACTION_TYPE, Const.BUNDLE_KEY.INTENT_ACTION_LIST);
        intent.putExtra(Const.BUNDLE_KEY.CHAPTER_ID, chapterId);
        intent.putExtra(Const.BUNDLE_KEY.CHAPTER_NAME, chapterName);
        startActivity(intent);
    }
}
