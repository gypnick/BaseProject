package com.iwisedev.baseproject.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Fragment里面嵌套Fragment
 */
public class FragmentChildController {

	private int containerId;
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;

	private FragmentChildController controller;

	public int getCurrPosition() {
		return currPosition;
	}

	//当前显示的fragment
	private int currPosition=0;



	public  void onDestroy() {
		controller = null;
	}

	public FragmentChildController(Fragment fragment, int containerId, ArrayList<Fragment> fragments) {
		this.containerId = containerId;
		fm = fragment.getChildFragmentManager();
		this.fragments = fragments;
		controller=this;
		initFragment();
	}

	private void initFragment() {

		//避免重复加载fragment
		if(fm.getFragments()==null||fm.getFragments().size()==0){
			FragmentTransaction ft = fm.beginTransaction();
			for(Fragment fragment : fragments) {
				ft.add(containerId,fragment);
			}

			ft.commitAllowingStateLoss();
		}else {
			fragments=(ArrayList<Fragment>) fm.getFragments();
		}

		currPosition=0;

	}

	public void showFragment(int position) {
		hideFragments();
		Fragment fragment = fragments.get(position);
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fragment);
		ft.commitAllowingStateLoss();
		currPosition=position;
	}

	public void hideFragments() {
		FragmentTransaction ft = fm.beginTransaction();
		for(Fragment fragment : fragments) {
			if(fragment != null) {
				ft.hide(fragment);
			}
		}
		ft.commitAllowingStateLoss();
	}

	public Fragment getFragment(int position) {
		return fragments.get(position);
	}
}