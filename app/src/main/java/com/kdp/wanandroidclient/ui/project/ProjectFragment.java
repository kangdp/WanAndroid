package com.kdp.wanandroidclient.ui.project;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.ui.adapter.ProjectFragPagerAdapter;
import com.kdp.wanandroidclient.ui.base.BaseTabFragment;


import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseTabFragment<ProjectCatePresenter> implements ProjectCateContract.IProjectCateView{

    private List<ProjectCate> cateList = new ArrayList<>();

    @Override
    protected ProjectCatePresenter createPresenter() {
        return new ProjectCatePresenter();
    }

    @Override
    public void setData(List<ProjectCate> data) {
        cateList.clear();
        cateList.addAll(data);
    }


    @Override
    public List<ProjectCate> getData() {
        return cateList;
    }
    @Override
    public void showContent() {
        ProjectFragPagerAdapter adapter = new ProjectFragPagerAdapter(getChildFragmentManager(),cateList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(cateList.size());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getProjectCate();
    }


    public void scrollToTop(){
        int id = cateList.get(viewPager.getCurrentItem()).getId();
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.PROJECT_LIST,new Event(Event.Type.SCROLL_TOP,id));
    }

}
