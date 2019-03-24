package com.kdp.wanandroidclient.ui.tree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.inter.OnItemClickListener;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.adapter.TreeAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;

import java.util.List;

/**
 * 知识体系
 * author: 康栋普
 * date: 2018/2/22
 */

public class TreeFragment extends BaseAbListFragment<TreePresenter, Tree> implements TreeContract.ITreeView, OnItemClickListener<Tree> {

    @Override
    protected TreePresenter createPresenter() {
        return new TreePresenter();
    }

    //加载列表数据
    @Override
    protected void loadDatas() {
        mPresenter.loadTree();
    }

    @Override
    protected BaseListAdapter<Tree> getListAdapter() {
        return new TreeAdapter(this);
    }

    @Override
    public void setData(List<Tree> data) {
        mListData.clear();
        mListData.addAll(data);
    }


    //进入子分类页面
    @Override
    public void onItemClick(int position,Tree mTree) {
        Intent intent = new Intent(getActivity(), TreeActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(Const.BUNDLE_KEY.OBJ, mTree);
        intent.putExtras(b);
        startActivity(intent);
    }
}
