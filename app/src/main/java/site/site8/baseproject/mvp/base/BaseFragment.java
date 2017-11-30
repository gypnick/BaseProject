package site.site8.baseproject.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import site.site8.baseproject.widget.KyLoadingBuilder;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

public abstract class BaseFragment<V extends BaseView, T extends BasePresenter<V>> extends RxFragment implements BaseView {
    private KyLoadingBuilder loadingView;
    protected T mPresenter;
    private LifecycleProvider<FragmentEvent> provider;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //判断是否使用MVP模式
        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        View rootView = inflater.inflate(provideContentViewId(), container, false);
       ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    public void init() {

    }

    public void initView(View rootView) {
    }

    public void initData() {

    }

    public void initListener() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 显示等待提示框
     */
    @Override
    public void showWaitingDialog() {
        hideWaitingDialog();
        loadingView = new KyLoadingBuilder(getActivity());
        loadingView.setCornerRadius(20);
        loadingView.setText("加载中");
        loadingView.show();
    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    /**
     * 隐藏等待提示框
     */
    @Override
    public void hideWaitingDialog() {
        if (loadingView != null) {
            loadingView.dismiss();
            loadingView = null;
        }
    }
    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();
}
