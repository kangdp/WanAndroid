package com.kdp.wanandroidclient.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * 事件线
 * author: 康栋普
 * date: 2018/4/11
 */

public class RxEvent {

    private static RxEvent mInstance;
    private Map<String, List<PublishSubject>> mSubjectMaps = new HashMap<>();

    public static RxEvent getInstance() {
        if (mInstance == null) {
            synchronized (RxEvent.class) {
                if (mInstance == null)
                    mInstance = new RxEvent();
            }
        }
        return mInstance;
    }


    /**
     * 注册事件
     *
     * @param mAction
     * @return
     */
    public PublishSubject registerEvent(String mAction) {
        List<PublishSubject> mSubjectList = mSubjectMaps.get(mAction);
        if (mSubjectList == null) {
            mSubjectList = new ArrayList<>();
        }
        mSubjectMaps.put(mAction, mSubjectList);
        PublishSubject mSubject = PublishSubject.create();
        mSubjectList.add(mSubject);
        return mSubject;
    }

    /**
     * 发送事件
     *
     * @param mAction
     * @param object
     */
    public void postEvent(String mAction, Object object) {
        List<PublishSubject> mSubjectList = mSubjectMaps.get(mAction);
        if (mSubjectList != null && !mSubjectList.isEmpty()) {
            for (PublishSubject mSubject : mSubjectList) {
                mSubject.onNext(object);
            }
        }
    }

    /**
     * 注销事件
     *
     * @param mAction
     * @param mSubject
     */
    public void unRegisterEvent(String mAction, PublishSubject mSubject, DisposableObserver mDisposable) {

        List<PublishSubject> mSubjectList = mSubjectMaps.get(mAction);
        //中断事件
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        if (mSubjectList != null){
            mSubjectList.remove(mSubject);
            if (mSubjectList.isEmpty()){
                mSubjectMaps.remove(mAction);

            }
        }
    }

}
