package site.site8.baseproject.mvp.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import site.site8.baseproject.R;
import site.site8.baseproject.app.MyApp;

import site.site8.baseproject.uitl.UIUtils;
import site.site8.baseproject.widget.KyLoadingBuilder;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends RxAppCompatActivity implements BaseView {

    protected T mPresenter;
    private KyLoadingBuilder loadingView;
    @Bind(R.id.back)
    protected View mBack;
    @Bind(R.id.back_tv)
    protected TextView back_tv;
    @Bind(R.id.title)
    protected TextView title_tv;
    @Bind(R.id.toolbar)
    protected RelativeLayout toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.activities.add(this);
        init();

        mPresenter = createPresenter();


        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);


        setToolBar();
        initView();
        initData();
        initListener();
    }
    public void setStatusBar(){
        //沉浸式状态栏
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorPrimaryDark), 10);
    }
    private void setToolBar() {
        toolBar.setVisibility(hasToolBar()?View.VISIBLE:View.GONE);
        mBack.setVisibility(isToolbarCanBack()?View.VISIBLE:View.GONE);
        mBack.setOnClickListener((v)->finish());
    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }



    //在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    public void init() {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {

    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    /**
     * 是否让Toolbar有返回按钮(默认可以，一般一个应用中除了主界面，其他界面都是可以有返回按钮的)
     */
    protected boolean isToolbarCanBack() {
        return true;
    }

    /**
     * 是否需要toolBar
     * @return
     */
    protected boolean hasToolBar() {
        return  true;
    }
    /**
     * 显示等待提示框
     */
    @Override
    public void showWaitingDialog() {
        hideWaitingDialog();
        loadingView = new KyLoadingBuilder(this);
        loadingView.setCornerRadius(20);
        loadingView.setText("加载中");
        loadingView.show();
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

    /**
     * 设置标题
     * @param title
     */
    public void setToolbarTitle(String title) {
        title_tv.setText(title);
    }



    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void jumpToWebViewActivity(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        jumpToActivity(intent);
    }


    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
