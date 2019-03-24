package com.kdp.wanandroidclient.ui.project;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxObserver;
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
        RxPageListObserver<Article> rxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> mData) {
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

    @Override
    public void collectArticle() {
        RxObserver<String> mCollectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                projectView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        projectModel.collectArticle(projectView.getArticleId(), mCollectRxObserver);
        addDisposable(mCollectRxObserver);
    }

    @Override
    public void unCollectArticle() {
        RxObserver<String> unCollectRxObserver = new RxObserver<String>(this) {

            @Override
            protected void onStart() {
            }

            @Override
            protected void onSuccess(String data) {
                projectView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }
        };
        projectModel.unCollectArticle(projectView.getArticleId(), unCollectRxObserver);
        addDisposable(unCollectRxObserver);
    }
}
