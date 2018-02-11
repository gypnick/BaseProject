package site.site8.baseproject.mvp.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableBody;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import site.site8.baseproject.R;
import site.site8.baseproject.app.MyApp;
import site.site8.baseproject.config.JsonConvert;
import site.site8.baseproject.uitl.LogUtils;
import site.site8.baseproject.uitl.SPUtils;
import site.site8.baseproject.uitl.UIUtils;
import site.site8.baseproject.widget.KyLoadingBuilder;

public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends RxAppCompatActivity implements BaseView {

    protected T mPresenter;
    private KyLoadingBuilder loadingView;
    @Bind(R.id.back)
    protected View mBack;
    @Bind(R.id.back_tv)
    protected TextView back_tv;
    @Bind(R.id.back_iv)
    protected ImageView back_iv;
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

        setStatusBar();
        setToolBar();
        initView();
        //不需要登录的直接请求数据，需要登录且已经登录就请求数据
//        LogUtils.sf("是否请求数据："+!needLogin()+"===="+(needLogin()&&!LoginMgr.getInstance().needLogin()));
        if((!needLogin())||(needLogin())){
            initData();
        }
        initListener();
    }

    public void setStatusBar(){
        //沉浸式状态栏
       StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorPrimaryDark), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingView != null) {
            loadingView.dismiss();
            loadingView = null;
        }
        MyApp.getMainHandler().removeCallbacksAndMessages(null);

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
        //需要登录的界面
        if(needLogin()){
//            LoginMgr.getInstance().go2Login(this);
            finish();
        }
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
     * 是否需要登录
     * @return
     */
    public boolean needLogin(){
        return false;
    }
    /**
     * 网络加载数据错误提示
     * @param e
     */
    public void showLoadToastError(String e){
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
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
    //==============================================回调请求=====================================================================

    protected HttpParams getHttpParams() {
        return new HttpParams();
    }

    protected HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }
    protected <T> void post(String url, Type type, HttpParams params, HttpHeaders httpHeaders, BasePresenter.ResultCallback<T> resultCallback) {
        OkGo.<T>post(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> showWaitingDialog())
                .doFinally(() -> hideWaitingDialog())
                .compose(bindLifecycle())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        resultCallback.onSucceed(t);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        if (e.getMessage().equals("-1002")) {
//                            finish();
//                            LoginMgr.getInstance().go2Login((BaseActivity.this));
//                        } else {
//                            showLoadToastError(e.getLocalizedMessage());
//                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    protected <T> void post(String url, Type type, BasePresenter.ResultCallback<T> resultCallback) {
        post(url,type,null,null,resultCallback);
    }
    protected <T> void post(String url, Type type,HttpParams params, BasePresenter.ResultCallback<T> resultCallback) {
        post(url,type,params,null,resultCallback);
    }

    protected  <T>  void postWithToken(String url, Type type, BasePresenter.ResultCallback<T> resultCallback) {
        HttpParams httpParams = getHttpParams();
//        httpParams.put("user_token", SPUtils.getUserToken());
        post(url,type,httpParams,null,resultCallback);
    }

    protected <T> void get(String url, Type type, HttpParams params, HttpHeaders httpHeaders, BasePresenter.ResultCallback<T> resultCallback) {
        OkGo.<T>get(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> showWaitingDialog())
                .doFinally(() ->hideWaitingDialog())
                .compose(bindLifecycle())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        resultCallback.onSucceed(t);
                    }

                    @Override
                    public void onError(Throwable e) {

//                        if (e.getMessage().equals("-1002")) {
//                            LoginMgr.getInstance().go2Login(BaseActivity.this);
//                            finish();
//                        } else {
//                           showLoadToastError(e.getLocalizedMessage());
//                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    protected <T> void get(String url, Type type, BasePresenter.ResultCallback<T> resultCallback) {
        get(url,type,null,null,resultCallback);
    }
    protected <T> void get(String url, Type type,HttpParams params, BasePresenter.ResultCallback<T> resultCallback) {
        get(url,type,params,null,resultCallback);
    }
    public interface ResultCallback<T> {
        public void onSucceed(T t);

        public void onError(String e);
    }

    /**
     *
     *
     */
//   EventBus.getDefault().register(this);
//    EventBus.getDefault().postSticky(new UserInfoUpdateEvent());
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void Event(NewMsgEvent newMsgEvent) {
//        messageIv.setImageResource(newMsgEvent.isHaveNewMsg() ? R.drawable.have_message : R.drawable.message);
//    }
}
