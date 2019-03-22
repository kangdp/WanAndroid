package com.kdp.wanandroidclient.ui.project;

import android.os.Bundle;

import com.kdp.wanandroidclient.bean.Project;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.adapter.ProjectListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;

import java.util.List;

public class ProjectListFragment extends BaseAbListFragment<ProjectPresenter, Project> implements ProjectContract.IProjectView{

    private int cid;//分类id
    public static ProjectListFragment instantiate(int cid){
        ProjectListFragment instance = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Const.BUNDLE_KEY.ID,cid);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected boolean isEnableLazy() {
        return true;
    }


    @Override
    protected void getBundle(Bundle bundle) {
        cid = bundle.getInt(Const.BUNDLE_KEY.ID);
    }

    @Override
    protected void loadDatas() {
        mPresenter.getProjectList();
    }

    @Override
    protected BaseListAdapter<Project> getListAdapter() {
        return new ProjectListAdapter();
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public int getCid() {
        return cid;
    }

    @Override
    public void setData(List<Project> data) {
        mListData.addAll(data);
    }

    @Override
    public int getFirstPage() {
        return 1;
    }
}
