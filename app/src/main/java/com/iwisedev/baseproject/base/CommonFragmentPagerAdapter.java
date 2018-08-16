package com.iwisedev.baseproject.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * @描述 通用的ViewPager适配器(FragmentPagerAdapter)
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragments;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

}
