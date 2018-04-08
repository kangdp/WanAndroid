package com.kdp.wanandroidclient.ui.user;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.utils.AppUtils;
import com.kdp.wanandroidclient.utils.LightStatusbarUtils;

/**
 * author: 康栋普
 * date: 2018/3/25
 */

public class AboutUsActivity extends AppCompatActivity {
    private TextView mVersionView, mIntroduceView;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle bundle) {
        LightStatusbarUtils.setLightStatusBar(this,false);
        super.onCreate(bundle);
        setContentView(R.layout.activity_about_us);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingbarlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.about_us);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置展开后的字体颜色
        mCollapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white)));
        //设置收缩后的字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white)));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVersionView = (TextView) findViewById(R.id.version);
        mIntroduceView = (TextView) findViewById(R.id.introduce);
        setVersion();
        setIntroduce();

    }

    private void setIntroduce() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mIntroduceView.setText(Html.fromHtml(getString(R.string.about_us_introduce), Html.FROM_HTML_MODE_LEGACY));
        } else {
            mIntroduceView.setText(Html.fromHtml(getString(R.string.about_us_introduce)));
        }
        //设置跳转
        mIntroduceView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setVersion() {
        String mVersionFormat = getString(R.string.version_format);
        String mVersionName = AppUtils.getVersionName(this);
        String mAppName = getString(R.string.app_name);
        String mVersionStr = String.format(mVersionFormat, mAppName, mVersionName);
        mVersionView.setText(mVersionStr);
    }
}
