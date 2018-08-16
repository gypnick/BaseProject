package com.iwisedev.baseproject.mvp.v;


import android.databinding.DataBindingUtil;
import android.widget.Toast;

import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.databinding.ActivityMainBinding;
import com.iwisedev.baseproject.demo.DemoMainActivity;
import com.iwisedev.baseproject.base.BaseActivity;
import com.iwisedev.baseproject.mvp.c.MainActivityContract;
import com.iwisedev.baseproject.mvp.p.MainActivityPresenter;
import com.iwisedev.baseproject.ui.Test;

import java.util.List;

public class MainActivity extends BaseActivity<MainActivityContract.View,MainActivityPresenter> implements MainActivityContract.View{
    private ActivityMainBinding binding;
    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this,this);
    }

    @Override
    protected void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        toolbar=binding.toolBar.toolbar;
        back = binding.toolBar.back;
        title_tv = binding.toolBar.title;
    }

//    @Override
//    protected int provideContentViewId() {
//        return R.layout.activity_main;
//    }



    @Override
    public void initView() {
       super.initView();
        jumpToActivity(DemoMainActivity.class);
        mPresenter.checkData();
    }


    @Override
    public void showError() {

    }

    @Override
    public void showData(List<Test> list) {
        Toast.makeText(this, "====>"+list.size(), Toast.LENGTH_SHORT).show();
    }
}
