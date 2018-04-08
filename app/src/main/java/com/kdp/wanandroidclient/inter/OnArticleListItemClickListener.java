package com.kdp.wanandroidclient.inter;

/**
 * author: 康栋普
 * date: 2018/2/27
 */

public interface OnArticleListItemClickListener {

    void onItemClick(String title,String url);

    void onCollectClick(int position,int id,int originId);
    void onCollectClick(int position,int id);

    void onTreeClick(int chapterId,String chapterName);
}
