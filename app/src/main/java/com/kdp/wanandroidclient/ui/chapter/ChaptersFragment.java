package com.kdp.wanandroidclient.ui.chapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.ui.adapter.ChaptersFragPagerAdapter;
import com.kdp.wanandroidclient.ui.base.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;

/***
 * @author kdp
 * @date 2019/3/25 17:00
 * @description
 */
public class ChaptersFragment extends BaseTabFragment<ChaptersPresenter> implements ChapterContract.IChaptersView{
    private List<Chapter> chapterList = new ArrayList<>();
    @Override
    protected ChaptersPresenter createPresenter() {
        return new ChaptersPresenter();
    }

    @Override
    public void setData(List<Chapter> data) {
        chapterList.clear();
        chapterList.addAll(data);
    }

    @Override
    public List<Chapter> getData() {
        return chapterList;
    }

    @Override
    public void showContent() {
        ChaptersFragPagerAdapter adapter = new ChaptersFragPagerAdapter(getChildFragmentManager(),chapterList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(chapterList.size());
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getChapters();
    }
}
