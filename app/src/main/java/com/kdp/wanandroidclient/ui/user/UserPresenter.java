package com.kdp.wanandroidclient.ui.user;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.UserModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;
import java.util.List;

/**
 * 和用户相关的Presenter
 * author: 康栋普
 * date: 2018/3/21
 */

public class UserPresenter extends BasePresenter<UserContract.IUserView> implements UserContract.IUserPresenter {
    private UserContract.IUserView mUserView;
    private UserModel mUserModel;

    UserPresenter() {
        this.mUserModel = new UserModel();
    }

    /**
     * 删除收藏
     */
    @Override
    public void deleteCollectArticle() {
        mUserView = getView();
        RxObserver<String> mDeleteRxObserver = new RxObserver<String>(this) {
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
            }
        };
        mUserModel.deleteCollectArticle(mUserView.getArticleId(), mUserView.getOriginId(), mDeleteRxObserver);
        addDisposable(mDeleteRxObserver);
    }

    /**
     * 获取收藏的列表
     */
    @Override
    public void loadCollectList() {
        mUserView = getView();
        RxPageListObserver<Article> mCollectRxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> mData) {
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
        };
        mUserModel.getCollectArticleList(mUserView.getPage(), mCollectRxPageListObserver);
        addDisposable(mCollectRxPageListObserver);
    }
}
