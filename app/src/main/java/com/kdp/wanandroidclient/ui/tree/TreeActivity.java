package com.kdp.wanandroidclient.ui.tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.ui.adapter.TreeFragPagerAdapter;
import com.kdp.wanandroidclient.ui.base.BaseTabActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识体系二级分类
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeActivity extends BaseTabActivity {
    private String mTitle;
    private List<Tree.ChildrenBean> mTreeDatas = new ArrayList<>();
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(mTitle);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
            Bundle bundle = intent.getExtras();
            Tree mTree = null;
            if (bundle != null) {
                mTree = (Tree) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
            }
            if (mTree != null) {
                mTitle = mTree.getName();
                mTreeDatas = mTree.getChildren();
            }
    }

    @Override
    protected FragmentPagerAdapter createFragPagerAdapter() {
        viewPager.setOffscreenPageLimit(mTreeDatas.size());
        return new TreeFragPagerAdapter(getSupportFragmentManager(), mTreeDatas);
    }

}
