package com.kdp.wanandroidclient.inter;

import com.kdp.wanandroidclient.bean.Article;

/***
 * @author kdp
 * @date 2019/3/23 14:41
 * @description
 */
public interface OnProjectListItemClickListener extends OnItemClickListener<Article> {
        void onCollectClick(int position,int id);
}
