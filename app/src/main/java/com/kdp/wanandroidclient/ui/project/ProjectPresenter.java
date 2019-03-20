package com.kdp.wanandroidclient.ui.project;

import com.kdp.wanandroidclient.bean.Project;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.ProjectModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

public class ProjectPresenter extends BasePresenter<ProjectContract.IProjectView> implements ProjectContract.IProjectPresenter {
    private ProjectModel projectModel;
    private ProjectContract.IProjectView projectView;

    ProjectPresenter() {
        this.projectModel = new ProjectModel();
    }

    @Override
    public void getProjectList() {
        projectView = getView();
        RxPageListObserver<Project> rxPageListObserver = new RxPageListObserver<Project>(this) {
            @Override
            public void onSuccess(List<Project> mData) {
                projectView.setData(mData);
                if (projectView.getData().size() == 0){
                    projectView.showEmpty();
                }else {
                    projectView.showContent();
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                projectView.showFail(errorMsg);
            }
        };
        projectModel.getProjectList(projectView.getPage(),projectView.getCid(),rxPageListObserver);
        addDisposable(rxPageListObserver);
    }
}
