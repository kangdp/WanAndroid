package com.kdp.wanandroidclient.ui.mvp.presenter;

import com.kdp.wanandroidclient.ui.mvp.view.IView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {
    private V view;
    private Map<String, Disposable> mDisposableMaps;

    /**
     * 绑定
     *
     * @param view
     */
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * 解绑
     */
    @Override
    public void detachView() {
        this.view = null;
    }

    /**
     * 获取目标view
     *
     * @return
     */
    @Override
    public V getView() {
        checkAttachView();
        return view;
    }

    /**
     * 验证是否已绑定此View
     *
     * @return
     */
    @Override
    public void checkAttachView() {
        if (view == null)
            throw new RuntimeException("You have no binding this view");
    }

    /**
     * @param tag
     * @param disposable
     */
    @Override
    public void addRequestTag(String tag, Disposable disposable) {
        if (mDisposableMaps == null)
            mDisposableMaps = new HashMap<>();
        mDisposableMaps.put(tag, disposable);
    }


    /**
     * 取消请求
     */
    @Override
    public void cancelRequestTags() {
        if (mDisposableMaps == null) return;
        Iterator<Map.Entry<String, Disposable>> mIterator = mDisposableMaps.entrySet().iterator();
        Disposable mDisposable;
        while (mIterator.hasNext()) {
            mDisposable = mIterator.next().getValue();
            if (!mDisposable.isDisposed())
                mDisposable.dispose();
        }
        mDisposableMaps.clear();
        mDisposableMaps = null;


    }


}
