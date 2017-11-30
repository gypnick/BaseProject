package site.site8.baseproject.demo;


import android.widget.Button;

import site.site8.baseproject.R;
import site.site8.baseproject.mvp.base.BaseActivity;
import site.site8.baseproject.mvp.base.BasePresenter;

import butterknife.Bind;

public class DemoMainActivity extends BaseActivity {


    @Bind(R.id.home_viewPager_activity)
    Button homeViewPagerActivity;
    @Bind(R.id.home_activity)
    Button homeActivity;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_demo_main;
    }

    @Override
    public void initListener() {
        super.initListener();
        homeActivity.setOnClickListener(v->jumpToActivity(HomeActivity.class));
        homeViewPagerActivity.setOnClickListener(v->jumpToActivity(HomeViewPagerActivity.class));
    }
}
