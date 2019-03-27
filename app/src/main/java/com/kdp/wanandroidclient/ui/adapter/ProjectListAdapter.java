package com.kdp.wanandroidclient.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.common.ListDataHolder;
import com.kdp.wanandroidclient.inter.OnProjectListItemClickListener;
import com.kdp.wanandroidclient.manager.ImageLoaderManager;
import com.kdp.wanandroidclient.utils.DateUtils;

public class ProjectListAdapter extends BaseListAdapter<Article> {
    private OnProjectListItemClickListener listener;

    public ProjectListAdapter(OnProjectListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_project_list;
    }
    @Override
    public void bindDatas(ListDataHolder holder, final Article bean, int itemType, final int position) {
        ImageView iv_img = holder.getView(R.id.iv_img);
        ImageView img_collect = holder.getView(R.id.img_collect);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_name = holder.getView(R.id.tv_name);

        ImageLoaderManager.displayImage(bean.getEnvelopePic(),iv_img, Const.IMAGE_LOADER.NOMAL_IMG);
        tv_title.setText(bean.getTitle());
        tv_desc.setText(bean.getDesc());
        tv_time.setText(DateUtils.parseTime(bean.getPublishTime()));
        tv_name.setText(bean.getAuthor());
        img_collect.setImageResource(bean.isCollect() ? R.drawable.ic_favorite_light_24dp : R.drawable.ic_favorite_gray_24dp);

        img_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onCollectClick(position,bean.getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemClick(position,bean);
            }
        });
    }
}
