package site.site8.baseproject.demo;

import site.site8.baseproject.R;
import site.site8.baseproject.mvp.base.BaseActivity;
import site.site8.baseproject.mvp.base.BasePresenter;

public class HomeViewPagerActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_home_view_pager;
    }
}
