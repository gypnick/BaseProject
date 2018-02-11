package site.site8.baseproject.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableBody;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import site.site8.baseproject.config.JsonConvert;
import site.site8.baseproject.uitl.SPUtils;
import site.site8.baseproject.widget.KyLoadingBuilder;

public abstract class BaseFragment<V extends BaseView, T extends BasePresenter<V>> extends RxFragment implements BaseView {
    private KyLoadingBuilder loadingView;
    protected T mPresenter;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void init() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingView != null) {
            loadingView.dismiss();
            loadingView = null;
        }
    }

    public void initView(View rootView) {
    }

    public void initData() {

    }

    public void initListener() {

    }

    /**
     * 网络加载数据错误提示
     * @param e
     */
    public void showLoadToastError(String e){
        Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
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

    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    public void jumpToWebViewActivity(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        jumpToActivity(intent);
    }


    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
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
//                            LoginMgr.getInstance().go2Login((getActivity()));
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
//                            LoginMgr.getInstance().go2Login(getActivity());
//                        } else {
//                            showLoadToastError(e.getLocalizedMessage());
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
}
