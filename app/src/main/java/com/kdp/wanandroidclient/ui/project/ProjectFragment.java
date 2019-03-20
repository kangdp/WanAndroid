package com.kdp.wanandroidclient.ui.project;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.ui.adapter.ProjectFragPagerAdapter;
import com.kdp.wanandroidclient.ui.base.BasePresenterFragment;
import com.kdp.wanandroidclient.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BasePresenterFragment<ProjectCatePresenter> implements ProjectCateContract.IProjectCateView{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ProjectCate> cateList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.base_tab_layout;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }

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
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getProjectCate();
    }
}
