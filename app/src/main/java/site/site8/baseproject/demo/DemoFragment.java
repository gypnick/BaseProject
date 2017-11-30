package site.site8.baseproject.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import site.site8.baseproject.R;
import site.site8.baseproject.mvp.base.BaseFragment;
import site.site8.baseproject.mvp.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by Ledev2 on 2017-10-12.
 */

public class DemoFragment extends BaseFragment {

    @Bind(R.id.content)
    TextView content;
    private String contents;

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
    protected int provideContentViewId() {
        return R.layout.demo_fragment_layout;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        content.setText(contents);
    }
}
