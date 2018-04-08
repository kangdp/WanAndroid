package com.kdp.wanandroidclient.ui.user;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.UserModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/21
 */

public class UserPresenter extends BasePresenter<UserContract.IUserView> implements UserContract.IUserPresenter {
    private UserContract.IUserView mUserView;
    private UserModel mUserModel;

    public UserPresenter() {
        this.mUserModel = new UserModel();
    }

    @Override
    public void deleteCollectArticle() {
        mUserView = getView();
        mUserModel.deleteCollectArticle(mUserView.getArticleId(),mUserView.getOriginId(), new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                mUserView.deleteCollect();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mUserView.showFail(errorMsg);
            }

            @Override
            public void showLoading() {
//                super.showLoading();
            }
        });
    }

    @Override
    public void loadArticleList() {
        mUserView = getView();
        mUserModel.getCollectArticleList(mUserView.getPage(), new RxPageListObserver<ArticleBean>(this, UserModel.class.getName()) {
            @Override
            public void onSuccess(List<ArticleBean> mData) {
                mUserView.setData(mData);
                if (mUserView.getData().size() == 0)
                    mUserView.showEmpty();
                else
                    mUserView.showContent();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                mUserView.showFail(errorMsg);
            }
        });
    }

    @Override
    public void loadWebList() {
    }
}
