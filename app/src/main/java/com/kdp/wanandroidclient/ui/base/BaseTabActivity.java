package com.kdp.wanandroidclient.ui.base;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kdp.wanandroidclient.R;

/***
 * @author kdp
 * @date 2019/3/16 17:11
 * @description
 */
public abstract class BaseTabActivity extends BaseActivity{

    private LinearLayout ll_tab_container;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initViews() {
        ll_tab_container = findViewById(R.id.ll_tab_container);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTabs();

    }

    private void initTabs() {
       TabItem[] tabItems  = initTabItem();
       if (tabItems == null || tabItems.length <=0) return;
        for (TabItem item : tabItems) {
           Button tab_btn = generateTabView();
        }
    }

    private Button generateTabView() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_tab_container.getLayoutParams();

        Button tab = new Button(this);
        return tab;
    }


    protected abstract TabItem[] initTabItem();


    public static class TabItem {
        public String title;
        public int resId;
    }


}

