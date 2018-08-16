package com.iwisedev.baseproject.demo;


import android.databinding.DataBindingUtil;
import android.widget.Button;

import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.base.BaseActivity;
import com.iwisedev.baseproject.base.BasePresenter;
import com.iwisedev.baseproject.databinding.ActivityDemoMainBinding;


public class DemoMainActivity extends BaseActivity {


//    @Bind(R.id.home_viewPager_activity)
    Button homeViewPagerActivity;
//    @Bind(R.id.home_activity)
    Button homeActivity;
    private ActivityDemoMainBinding binding;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo_main);
        toolbar=binding.toolBar.toolbar;
        back = binding.toolBar.back;
        title_tv = binding.toolBar.title;
        homeActivity=binding.homeActivity;
        homeViewPagerActivity=binding.homeViewPagerActivity;
    }

//    @Override
//    protected int provideContentViewId() {
//        return R.layout.activity_demo_main;
//    }

    @Override
    public void initListener() {
        super.initListener();
        homeActivity.setOnClickListener(v->jumpToActivity(HomeActivity.class));
        homeViewPagerActivity.setOnClickListener(v->jumpToActivity(HomeViewPagerActivity.class));
    }
}
