package com.kdp.wanandroidclient.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.ui.chapter.ChapterListFragment;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/25 17:22
 * @description
 */
public class ChaptersFragPagerAdapter extends FragmentPagerAdapter {
    private List<Chapter> list;
    public ChaptersFragPagerAdapter(FragmentManager fm,List<Chapter> list) {
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int positions) {
        return ChapterListFragment.instantiate(list.get(positions).getId());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }
}
