package com.kdp.wanandroidclient.ui.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.kdp.wanandroidclient.R;

/***
 * @author kdp
 * @date 2019/3/19 9:53
 * @description
 */
public abstract class BaseTabActivity extends BaseActivity{

    private TabLayout tabLayout;
    protected ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.base_tab_layout;
    }

    @Override
    protected void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
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
