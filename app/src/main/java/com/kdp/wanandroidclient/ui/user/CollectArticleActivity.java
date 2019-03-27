package com.kdp.wanandroidclient.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.ui.adapter.ArticleListAdapter;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListActivity;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;

import java.util.List;

/**
 * 收藏的文章
 * author: 康栋普
 * date: 2018/3/21
 */

public class CollectArticleActivity extends BaseAbListActivity<UserPresenter, Article> implements UserContract.IUserView, OnArticleListItemClickListener {

    private int id;//文章id
    private int originId;//首页列表的文章id
    private int position;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.favorite_article);
        return true;
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected View initHeaderView() {
        return null;
    }


    @Override
    protected void loadDatas() {
        mPresenter.loadCollectList();
    }

    @Override
    protected BaseListAdapter<Article> getListAdapter() {
        return new ArticleListAdapter(this, Const.LIST_TYPE.COLLECT);
    }


    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void setData(List<Article> data) {
        mListData.addAll(data);
    }


    @Override
    public void onItemClick(int position,Article bean) {
        Intent intent = new Intent(this, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public int getArticleId() {
        return id;
    }

    @Override
    public void onDeleteCollectClick(int position, int id, int originId) {
        this.id = id;
        this.originId = originId;
        this.position = position;
        mPresenter.deleteCollectArticle();
    }

    @Override
    public void onCollectClick(int position, int id) {

    }

    @Override
    public int getOriginId() {
        return originId;
    }

    //删除收藏
    @Override
    public void deleteCollect() {
        if (mListData.size() > 1) {
            mListData.remove(position);
            mListAdapter.notifyItemDataRemove(position, mRecyclerView);
        } else {
            loadDatas();
        }
    }
}
