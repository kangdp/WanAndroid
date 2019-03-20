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
    private int mAction, mChapterId;
    private String mChapterName;
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(mTitle);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
        mAction = intent.getIntExtra(Const.BUNDLE_KEY.INTENT_ACTION_TYPE, 0);
        if (mAction == Const.BUNDLE_KEY.INTENT_ACTION_TREE) {
            Bundle bundle = intent.getExtras();
            Tree mTree = null;
            if (bundle != null) {
                mTree = (Tree) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
            }
            if (mTree != null) {
                mTitle = mTree.getName();
                mTreeDatas = mTree.getChildren();
            }
        } else {
            mChapterId = intent.getIntExtra(Const.BUNDLE_KEY.CHAPTER_ID, 0);
            mChapterName = intent.getStringExtra(Const.BUNDLE_KEY.CHAPTER_NAME);
            mTitle = mChapterName;
        }


    }

    @Override
    protected FragmentPagerAdapter createFragPagerAdapter() {
        TreeFragPagerAdapter mAdapter;
        if (mAction == Const.BUNDLE_KEY.INTENT_ACTION_TREE) {
            mAdapter = new TreeFragPagerAdapter(getSupportFragmentManager(), mAction, mTreeDatas);
        } else {
            mAdapter = new TreeFragPagerAdapter(getSupportFragmentManager(), mAction, mChapterId, mChapterName);
        }
        return mAdapter;
    }

}
