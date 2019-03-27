package com.kdp.wanandroidclient.ui.tree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.ui.adapter.TreeFragPagerAdapter;
import com.kdp.wanandroidclient.ui.base.BaseTabActivity;
import com.kdp.wanandroidclient.utils.ViewAnimatorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识体系二级分类
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeActivity extends BaseTabActivity {
    public ViewAnimatorHelper viewAnimatorHelper;
    private String mTitle;
    private List<Tree.ChildrenBean> mTreeDatas = new ArrayList<>();
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(mTitle);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
            Bundle bundle = intent.getExtras();
            Tree mTree = null;
            if (bundle != null) {
                mTree = (Tree) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
            }
            if (mTree != null) {
                mTitle = mTree.getName();
                mTreeDatas = mTree.getChildren();
            }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected FragmentPagerAdapter createFragPagerAdapter() {
        btn_scroll_top.setVisibility(View.VISIBLE);
        btn_scroll_top.setOnClickListener(onScrollTopListener);
        viewPager.setOffscreenPageLimit(mTreeDatas.size());
        return new TreeFragPagerAdapter(getSupportFragmentManager(), mTreeDatas);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        viewAnimatorHelper = new ViewAnimatorHelper();
        viewAnimatorHelper.bindView(btn_scroll_top);
    }

    private View.OnClickListener onScrollTopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           int id = mTreeDatas.get(viewPager.getCurrentItem()).getId();
           RxEvent.getInstance().postEvent(Const.EVENT_ACTION.SYSTEM_LIST,new Event(Event.Type.SCROLL_TOP,id));
        }
    };


    public void scroll(int offsetY){
        if (offsetY > 0 && btn_scroll_top.getVisibility() != View.INVISIBLE && !viewAnimatorHelper.isAnimating()){
            viewAnimatorHelper.hideFloatActionButton();
        }else if (offsetY < 0 && btn_scroll_top.getVisibility() != View.VISIBLE){
            viewAnimatorHelper.showFloatActionButton();
        }
    }
}
