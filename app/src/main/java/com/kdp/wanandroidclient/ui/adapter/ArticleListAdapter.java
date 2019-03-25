package com.kdp.wanandroidclient.ui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.common.ListDataHolder;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.utils.DateUtils;

/**
 * 文章列表
 * author: 康栋普
 * date: 2018/2/12
 */

public class ArticleListAdapter extends BaseListAdapter<Article> {

    private int Type;
    private OnArticleListItemClickListener listener;

    public ArticleListAdapter(OnArticleListItemClickListener listener, int type) {
        this.listener = listener;
        this.Type = type;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_article_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final Article bean, int itemType, final int position) {
        TextView tv_tag = holder.getView(R.id.tv_tag);
        TextView tv_author = holder.getView(R.id.tv_author);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_type = holder.getView(R.id.tv_type);
        ImageView img_collect = holder.getView(R.id.img_collect);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_title.setText(Html.fromHtml(bean.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_title.setText(Html.fromHtml(bean.getTitle()));
        }

        tv_author.setText(bean.getAuthor());
        tv_time.setText(DateUtils.parseTime(bean.getPublishTime()));


        if (Type == Const.LIST_TYPE.HOME || Type == Const.LIST_TYPE.SEARCH){
            coverToArticleList(tv_type,tv_tag,img_collect,position,bean);
        }else if (Type == Const.LIST_TYPE.TREE){
            coverToTreeList(tv_type,tv_tag,img_collect,position,bean);
        }else if (Type == Const.LIST_TYPE.COLLECT){
            coverToCollectList(tv_type,tv_tag,img_collect,position,bean);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position,bean);
                }
            }
        });
    }


    /**
     * 首页、搜索的文章列表
     * @param tv_type
     * @param tv_tag
     * @param img_collect
     * @param position
     * @param bean
     */
    private void coverToArticleList(TextView tv_type,TextView tv_tag, ImageView img_collect, final int position, final Article bean) {
        tv_type.setText(String.format("%1$s / %2$s",bean.getSuperChapterName(), bean.getChapterName()));
        tv_tag.setVisibility(View.VISIBLE);

        if (bean.isTop()){
            tv_tag.setActivated(true);
            tv_tag.setText("置顶");
            tv_tag.setTextColor(Color.RED);
        }else if (bean.isFresh()){
            tv_tag.setText("新");
            tv_tag.setTextColor(Color.RED);
            tv_tag.setActivated(true);
        }else if (bean.getTags().size() > 0){
            tv_tag.setActivated(false);
            tv_tag.setText(bean.getTags().get(0).getName());
            tv_tag.setTextColor(ContextCompat.getColor(AppContext.getContext(),R.color._009a61));
        }else  {
            tv_tag.setVisibility(View.GONE);
        }

        img_collect.setImageResource(bean.isCollect() ? R.drawable.ic_favorite_light_24dp : R.drawable.ic_favorite_gray_24dp);
        img_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCollectClick(position, bean.getId());
                }
            }
        });
    }

    /**
     * 体系文章列表
     * @param tv_tag
     */
    private void coverToTreeList(TextView tv_type, TextView tv_tag, ImageView img_collect, final int position, final Article bean) {
        tv_type.setText(String.format("%1$s / %2$s",bean.getSuperChapterName(), bean.getChapterName()));
        tv_tag.setVisibility(View.GONE);
        img_collect.setImageResource(bean.isCollect() ? R.drawable.ic_favorite_light_24dp : R.drawable.ic_favorite_gray_24dp);
        img_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCollectClick(position, bean.getId());
                }
            }
        });
    }

    /**
     * 收藏的文章列表
     * @param tv_type
     * @param img_collect
     * @param position
     * @param bean
     */
    private void coverToCollectList(TextView tv_type,TextView tv_tag,ImageView img_collect, final int position, final Article bean) {
        tv_type.setText(bean.getChapterName());
        tv_tag.setVisibility(View.GONE);
        img_collect.setImageResource(R.drawable.ic_favorite_light_24dp);
        img_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteCollectClick(position, bean.getId(), bean.getOriginId());
                }
            }
        });
    }

}
