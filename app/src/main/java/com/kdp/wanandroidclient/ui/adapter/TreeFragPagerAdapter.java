package com.kdp.wanandroidclient.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.ui.tree.TreeListFragment;

import java.util.List;

/**
 * fragmetn适配器
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeFragPagerAdapter extends FragmentPagerAdapter {
    private List<Tree.ChildrenBean> mTreeDatas;

    public TreeFragPagerAdapter(FragmentManager fm, List<Tree.ChildrenBean> mTreeDatas) {
        super(fm);
        this.mTreeDatas = mTreeDatas;
    }

    @Override
    public Fragment getItem(int position) {
          return TreeListFragment.instantiate(mTreeDatas.get(position).getId());
    }

    @Override
    public int getCount() {
        return mTreeDatas != null ? mTreeDatas.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTreeDatas.get(position).getName();
    }
}
