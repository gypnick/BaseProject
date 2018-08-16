package com.iwisedev.baseproject.demo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.base.BaseActivity;
import com.iwisedev.baseproject.base.FragmentController;
import com.iwisedev.baseproject.databinding.ActivityHomeBinding;
import com.iwisedev.baseproject.ui.Test;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity<HomeActivityContract.View, HomeActivityPresenter> implements HomeActivityContract.View {

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

//    @Bind(R.id.tabLayout)
    CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private FragmentController fragmentController;
    private ActivityHomeBinding binding;

    @Override
    protected HomeActivityPresenter createPresenter() {
        return new HomeActivityPresenter(this);
    }

    @Override
    protected void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        toolbar=binding.toolBar.toolbar;
        back = binding.toolBar.back;
        title_tv = binding.toolBar.title;
        tabLayout=binding.tabLayout;
    }

    @Override
    public void initView() {
        super.initView();


        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(DemoFragment.newInstance(mTitles[i]));
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        tabLayout.setTabData(mTabEntities);
        fragmentController = new FragmentController(this, R.id.fragment, fragments);
        fragmentController.showFragment(0);
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.checkData();
    }

    @Override
    protected boolean isToolbarCanBack() {
        return false;
    }

    @Override
    public void initListener() {
        super.initListener();
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                setToolbarTitle(mTitles[position]);
                fragmentController.showFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    public void showError() {

    }

    @Override
    public void showData(List<Test> list) {
        Toast.makeText(this, "数据：" + list.size(), Toast.LENGTH_SHORT).show();
    }
}
