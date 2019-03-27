package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.net.callback.RxObserver;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/25 16:34
 * @description
 */
public interface IChapterModel {

    /**
     * 获取公众号
     * @param rxObserver
     */
    void getChapters(RxObserver<List<Chapter>> rxObserver);
}
