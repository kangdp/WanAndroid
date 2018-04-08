package com.kdp.wanandroidclient.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: 康栋普
 * date: 2018/2/11
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null)
            getBundle(bundle);
    }

    protected abstract void getBundle(Bundle bundle);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            initViews(view);
        }
        return view;
    }

    protected abstract void initViews(View view);

    protected abstract int getLayoutId();



}
