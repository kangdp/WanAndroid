package com.kdp.wanandroidclient.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.ui.tree.TreeListFragment;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeFragPageAdapter extends FragmentPagerAdapter {
    private List<TreeBean.ChildrenBean> mTreeDatas;
    private int mAction, mChapterId;
    private String mChapterName;

    public TreeFragPageAdapter(FragmentManager fm, int mAction, List<TreeBean.ChildrenBean> mTreeDatas) {
        super(fm);
        this.mAction = mAction;
        this.mTreeDatas = mTreeDatas;
    }

    public TreeFragPageAdapter(FragmentManager fm, int mAction, int mChapterId, String mChapterName) {
        super(fm);
        this.mAction = mAction;
        this.mChapterId = mChapterId;
        this.mChapterName = mChapterName;
    }

    @Override
    public Fragment getItem(int position) {
        if (Const.BUNDLE_KEY.INTENT_ACTION_TREE == mAction)
            return TreeListFragment.instantiate(mTreeDatas.get(position).getId());
        else if (Const.BUNDLE_KEY.INTENT_ACTION_LIST == mAction)
            return TreeListFragment.instantiate(mChapterId);
        return null;
    }

    @Override
    public int getCount() {
        if (Const.BUNDLE_KEY.INTENT_ACTION_TREE == mAction)
            return mTreeDatas != null ? mTreeDatas.size() : 0;
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (Const.BUNDLE_KEY.INTENT_ACTION_TREE == mAction)
            return mTreeDatas.get(position).getName();
        return mChapterName;
    }
}
