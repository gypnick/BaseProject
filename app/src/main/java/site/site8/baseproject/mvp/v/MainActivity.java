package site.site8.baseproject.mvp.v;


import android.widget.Toast;

import site.site8.baseproject.R;
import site.site8.baseproject.demo.DemoMainActivity;
import site.site8.baseproject.mvp.base.BaseActivity;
import site.site8.baseproject.mvp.contract.MainActivityContract;
import site.site8.baseproject.mvp.p.MainActivityPresenter;
import site.site8.baseproject.ui.Test;

import java.util.List;

public class MainActivity extends BaseActivity<MainActivityContract.View,MainActivityPresenter> implements MainActivityContract.View{

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this,this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }



    @Override
    public void initView() {
       super.initView();
        jumpToActivity(DemoMainActivity.class);
//        mPresenter.checkData();
    }


    @Override
    public void showError() {

    }

    @Override
    public void showData(List<Test> list) {
        Toast.makeText(this, "====>"+list.size(), Toast.LENGTH_SHORT).show();
    }
}
