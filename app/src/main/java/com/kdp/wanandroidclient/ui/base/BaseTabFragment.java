package com.kdp.wanandroidclient.ui.base;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

/***
 * @author kdp
 * @date 2019/3/25 16:14
 * @description
 */
public abstract class BaseTabFragment<P extends BasePresenter> extends BasePresenterFragment<P>{
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    @Override
    protected int getLayoutId() {
        return R.layout.base_tab_layout;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }
}
