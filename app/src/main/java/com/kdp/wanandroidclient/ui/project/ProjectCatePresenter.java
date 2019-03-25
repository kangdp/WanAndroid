package com.kdp.wanandroidclient.ui.project;
import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.ProjectCateModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/20 17:00
 * @description
 */
public class ProjectCatePresenter extends BasePresenter<ProjectCateContract.IProjectCateView> implements ProjectCateContract.IProjectCatePresenter{
    private ProjectCateModel projectCateModel;
    private ProjectCateContract.IProjectCateView projectCateView;

    ProjectCatePresenter() {
        this.projectCateModel = new ProjectCateModel();
    }

    @Override
    public void getProjectCate() {
        projectCateView = getView();
        RxObserver<List<ProjectCate>> rxObserver = new RxObserver<List<ProjectCate>>(this) {
            @Override
            protected void onSuccess(List<ProjectCate> data) {
                projectCateView.setData(data);
                if (projectCateView.getData().size() == 0){
                    projectCateView.showEmpty();
                }else {
                    projectCateView.showContent();
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                projectCateView.showFail(errorMsg);
            }
        };

        projectCateModel.getProjectCate(rxObserver);
        addDisposable(rxObserver);

    }
}
