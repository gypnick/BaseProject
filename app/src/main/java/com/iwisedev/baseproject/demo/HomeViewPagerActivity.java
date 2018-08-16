package com.iwisedev.baseproject.demo;

import android.databinding.DataBindingUtil;

import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.base.BaseActivity;
import com.iwisedev.baseproject.base.BasePresenter;
import com.iwisedev.baseproject.databinding.ActivityHomeBinding;
import com.iwisedev.baseproject.databinding.ActivityHomeViewPagerBinding;

public class HomeViewPagerActivity extends BaseActivity {
    private ActivityHomeViewPagerBinding binding;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_view_pager);
        toolbar = binding.toolBar.toolbar;
        back = binding.toolBar.back;
        title_tv = binding.toolBar.title;
    }
//
//    @Override
//    protected int provideContentViewId() {
//        return R.layout.activity_home_view_pager;
//    }
}
