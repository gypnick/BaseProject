package com.iwisedev.baseproject.demo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.base.BaseFragment;
import com.iwisedev.baseproject.base.BasePresenter;
import com.iwisedev.baseproject.databinding.FragmentDemoBinding;


/**
 * Created by Ledev2 on 2017-10-12.
 */

public class DemoFragment extends BaseFragment {

    TextView content;
    private String contents;
    private FragmentDemoBinding binding;

    public static DemoFragment newInstance(String content) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            contents = getArguments().getString("content");
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected View initDataBindingViewId(LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_demo, container, false);

        return binding.getRoot();
    }
    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        content = binding.content;
        content.setText(contents);
    }
}
