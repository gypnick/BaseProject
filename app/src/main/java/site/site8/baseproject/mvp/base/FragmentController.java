package site.site8.baseproject.mvp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;


public class FragmentController {

	private int containerId;
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;

	private  FragmentController controller;



	public  void onDestroy() {
		controller = null;
	}

	public FragmentController(FragmentActivity activity, int containerId, ArrayList<Fragment> fragments) {
		this.containerId = containerId;
		fm = activity.getSupportFragmentManager();
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

	}

	public void showFragment(int position) {
		hideFragments();
		Fragment fragment = fragments.get(position);
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fragment);
		ft.commitAllowingStateLoss();
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