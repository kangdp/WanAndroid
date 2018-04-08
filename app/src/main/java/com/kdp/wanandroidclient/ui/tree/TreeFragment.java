package com.kdp.wanandroidclient.ui.tree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.inter.OnTreeItemClickListener;
import com.kdp.wanandroidclient.ui.adapter.TreeAdapter;
import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;

import java.util.List;

/**
 * 知识体系
 * author: 康栋普
 * date: 2018/2/22
 */

public class TreeFragment extends BaseAbListFragment<TreePresenter, TreeContract.ITreeView, TreeBean> implements TreeContract.ITreeView, OnTreeItemClickListener {

    @Override
    protected TreePresenter createPresenter() {
        return new TreePresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.loadTree();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new TreeAdapter(this);
    }


    @Override
    protected boolean isCanLoadMore() {
        return false;
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

    @Override
    public void setData(List<TreeBean> data) {
        mListData.addAll(data);
    }

    @Override
    public void onItemClick(TreeBean mTreeBean) {
        Intent intent = new Intent(getActivity(), TreeActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.INTENT_ACTION_TYPE,Const.BUNDLE_KEY.INTENT_ACTION_TREE);
        Bundle b = new Bundle();
        b.putSerializable(Const.BUNDLE_KEY.OBJ, mTreeBean);
        intent.putExtras(b);
        startActivity(intent);
    }
}
