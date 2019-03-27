package com.kdp.wanandroidclient.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.manager.ImageLoaderManager;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;

import java.util.List;

/**
 * 广告
 * author: 康栋普
 * date: 2018/3/7
 */

public class BannerAdapter extends PagerAdapter {

    private SparseArray<View> mViews;
    private List<Banner> mBannerDatas;


    public BannerAdapter(List<Banner> mBannerDatas) {
        this.mBannerDatas = mBannerDatas;
        mViews = new SparseArray<>();
    }

    public void notifyDatas(List<Banner> mBannerDatas) {
        this.mBannerDatas = mBannerDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mBannerDatas == null) return 0;
        return mBannerDatas.size() <= 1 ? mBannerDatas.size() : Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        View view = mViews.get(position);
        if (view == null) {
            position %= mBannerDatas.size();
            final Banner bean = mBannerDatas.get(position);
            view = LayoutInflater.from(AppContext.getContext()).inflate(R.layout.item_banner, container, false);
            ImageView imageView =  view.findViewById(R.id.img);
            TextView titleView =  view.findViewById(R.id.title);
            ImageLoaderManager.displayImage(bean.getImagePath(), imageView, Const.IMAGE_LOADER.NOMAL_IMG);
            titleView.setText(bean.getTitle());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(container.getContext(), WebViewActivity.class);
                    Article mArticle = new Article();
                    mArticle.setTitle(bean.getTitle());
                    mArticle.setLink(bean.getUrl());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.BUNDLE_KEY.OBJ, mArticle);
                    intent.putExtras(bundle);
                    container.getContext().startActivity(intent);
                }
            });
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public int getItemPosition(Object object) {
        mViews.clear();
        return POSITION_NONE;
    }

//    @Override
//    public void startUpdate(ViewGroup container) {
//        super.startUpdate(container);

        //ViewPager显示的页面数据有所改变的回调(还未处理)
//    }

//    @Override
//    public void finishUpdate(ViewGroup container) {
//        super.finishUpdate(container);

        //页面数据改变的处理结束后的回调
        //此处的处理其实就是 回调instantiateItem和destroyItem方法
        //当数据发生变化
//        int position = viewPager.getCurrentItem();
//        if (position == 0){
//            viewPager.setCurrentItem(8,false);
//        }else if (position == 9){
//            viewPager.setCurrentItem(1,false);
//        }
//    }


}
