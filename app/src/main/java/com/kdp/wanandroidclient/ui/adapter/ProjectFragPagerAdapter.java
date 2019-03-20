package com.kdp.wanandroidclient.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.ui.project.ProjectListFragment;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/20 17:42
 * @description
 */
public class ProjectFragPagerAdapter extends FragmentPagerAdapter {

    private List<ProjectCate> projectCateList;
    public ProjectFragPagerAdapter(FragmentManager fm,List<ProjectCate> projectCateList) {
        super(fm);
        this.projectCateList = projectCateList;
    }

    @Override
    public Fragment getItem(int position) {
        return ProjectListFragment.instantiate(projectCateList.get(position).getId());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return projectCateList.get(position).getName();
    }

    @Override
    public int getCount() {
        return projectCateList == null ? 0:projectCateList.size();
    }
}
