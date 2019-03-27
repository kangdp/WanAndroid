package com.kdp.wanandroidclient.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;

/***
 * @author kdp
 * @date 2019/3/19 9:53
 * @description
 */
public abstract class BaseTabActivity extends BaseActivity{

    private TabLayout tabLayout;
    protected ViewPager viewPager;
    protected FloatingActionButton btn_scroll_top;


    @Override
    protected int getLayoutId() {
        return R.layout.base_tab_layout;
    }

    @Override
    protected void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btn_scroll_top = findViewById(R.id.btn_scroll_top);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentPagerAdapter fragPagerAdapter = createFragPagerAdapter();
        if (fragPagerAdapter != null){
            viewPager.setAdapter(fragPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }


    protected abstract FragmentPagerAdapter createFragPagerAdapter();
}
