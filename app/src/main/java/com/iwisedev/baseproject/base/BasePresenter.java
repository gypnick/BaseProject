package com.iwisedev.baseproject.base;

import android.app.Activity;
import android.content.Context;


import com.iwisedev.baseproject.config.JsonConvert;
import com.iwisedev.baseproject.uitl.LogUtils;
import com.iwisedev.baseproject.uitl.NetUtils;
import com.iwisedev.baseproject.uitl.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONException;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends BaseView> {

    public V mView;
    public Context mContext;

    public BasePresenter(V view, Context context) {
        mView = view;
        mContext = context;
    }

    protected HttpParams getHttpParams() {
        return new HttpParams();
    }

    protected HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    public BasePresenter(V view) {
        mView = view;
    }
/*================== 以下是网络请求接口 ==================*/
//Type type =  new TypeToken<ResponseBean<List<SendPlaySceneryBean>>>() {}.getType();

//    protected <T> Observable<T> getData(String url, Type type) {
//        return getData(url, type, null, null);
//    }

//    protected <T> Observable<T> getData(String url, Type type, HttpParams params) {
//        Observable<T> observable = OkGo.<T>get(url)
//                .params(params)
//                .converter(new JsonConvert<T>(type) {
//                })
//                .adapt(new ObservableBody<T>())
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((m) -> mView.showWaitingDialog())
//                .doFinally(() -> mView.hideWaitingDialog())
//                .compose(mView.bindLifecycle());
//        return observable;
//    }

//    protected <T> Observable<T> getData(String url, Type type, HttpHeaders httpHeaders, HttpParams params) {
//        Observable<T> observable = OkGo.<T>get(url)
//                .params(params)
//                .headers(httpHeaders)
//                .converter(new JsonConvert<T>(type) {
//                })
//                .adapt(new ObservableBody<T>())
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((m) -> mView.showWaitingDialog())
//                .doFinally(() -> mView.hideWaitingDialog())
//                .compose(mView.bindLifecycle());
//        return observable;
//    }

//    protected <T> Observable<T> postData(String url, Type type, HttpHeaders httpHeaders, HttpParams params) {
//        Observable<T> observable = OkGo.<T>post(url)
//                .params(params)
//                .headers(httpHeaders)
//                .converter(new JsonConvert<T>(type) {
//                })
//                .adapt(new ObservableBody<T>())
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((m) -> mView.showWaitingDialog())
//                .doFinally(() -> mView.hideWaitingDialog())
//                .compose(mView.bindLifecycle());
//        return observable;
//    }
//
//    protected <T> Observable<T> postData(String url, Type type, HttpParams params) {
//        Observable<T> observable = OkGo.<T>post(url)
//                .params(params)
//                .converter(new JsonConvert<T>(type) {
//                })
//                .adapt(new ObservableBody<T>())
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((m) -> mView.showWaitingDialog())
//                .doFinally(() -> mView.hideWaitingDialog())
//                .compose(mView.bindLifecycle());
//        return observable;
//    }
//
//    protected <T> Observable<T> postData(String url, Type type) {
//        return postData(url, type, null, null);
//    }


//    public <T> Observable<T> postDataByToken(String url, Type type) {
//        HttpParams httpParams = new HttpParams();
//        httpParams.put("user_token", SPUtils.getUserToken());
//        return postData(url, type, null, httpParams);
//    }

    //==============================================回调请求=====================================================================
    protected <T> void post(String url, Type type, HttpParams params, HttpHeaders httpHeaders, ResultCallback<T> resultCallback) {
        OkGo.<T>post(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> mView.showWaitingDialog())
                .doFinally(() -> mView.hideWaitingDialog())
                .compose(mView.bindLifecycle())
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
                        if (e instanceof JSONException) {
                            LogUtils.e("json解析错误："+getClass().getName());
                            mView.showLoadToastError("服务器错误");
                        }
//                        else  if (e.getMessage().equals("-1002")) {
//                            LoginMgr.getInstance().go2Login(((Activity) mContext));
//                        }
                        else if (!NetUtils.isConnectedAndToast(mContext)) {
                        }else {
                            mView.showLoadToastError(e.getLocalizedMessage());
                        }
                        resultCallback.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    protected <T> void postNoLoading(String url, Type type, HttpParams params, HttpHeaders httpHeaders, ResultCallback<T> resultCallback) {
        OkGo.<T>post(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((m) -> mView.showWaitingDialog())
//                .doFinally(() -> mView.hideWaitingDialog())
                .compose(mView.bindLifecycle())
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
                        if (e instanceof JSONException) {
                            LogUtils.e("json解析错误："+getClass().getName());
                            mView.showLoadToastError("服务器错误");
                        }
//                        else if (e.getMessage().equals("-1002")) {
//                            LoginMgr.getInstance().go2Login(((Activity) mContext));
//                        }
                        else if (!NetUtils.isConnectedAndToast(mContext)) {
                        }  else {
                            mView.showLoadToastError(e.getLocalizedMessage());

                        }
                        resultCallback.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    protected <T> void postNoLoading(String url, Type type, ResultCallback<T> resultCallback) {
        postNoLoading(url, type, null, null, resultCallback);
    }

    protected <T> void postNoLoading(String url, Type type, HttpParams params, ResultCallback<T> resultCallback) {
        postNoLoading(url, type, params, null, resultCallback);
    }

    protected <T> void post(String url, Type type, HttpParams params, boolean needLoading, ResultCallback<T> resultCallback) {
        if (needLoading) {
            post(url, type, params, null, resultCallback);
        } else {
            postNoLoading(url, type, params, null, resultCallback);
        }
    }

    protected <T> void post(String url, Type type, ResultCallback<T> resultCallback) {
        post(url, type, null, null, resultCallback);
    }

    protected <T> void post(String url, Type type, HttpParams params, ResultCallback<T> resultCallback) {
        post(url, type, params, null, resultCallback);
    }

    protected <T> void postWithToken(String url, Type type, ResultCallback<T> resultCallback) {
        HttpParams httpParams = getHttpParams();
//        httpParams.put("user_token", SPUtils.getUserToken());
        post(url, type, httpParams, null, resultCallback);
    }

    protected <T> void postWithTokenNoloading(String url, Type type, ResultCallback<T> resultCallback) {
        HttpParams httpParams = getHttpParams();
//        httpParams.put("user_token", SPUtils.getUserToken());
        postNoLoading(url, type, httpParams, null, resultCallback);
    }

    protected <T> void get(String url, Type type, HttpParams params, HttpHeaders httpHeaders, ResultCallback<T> resultCallback) {
        OkGo.<T>get(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> mView.showWaitingDialog())
                .doFinally(() -> mView.hideWaitingDialog())
                .compose(mView.bindLifecycle())
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
                        if (e instanceof JSONException) {
                            LogUtils.e("json解析错误："+getClass().getName());
                            mView.showLoadToastError("服务器错误");
                        }
//                        else  if (e.getMessage().equals("-1002")) {
//                            LoginMgr.getInstance().go2Login(((Activity) mContext));
//                        }

                        else if (!NetUtils.isConnectedAndToast(mContext)) {
                        }  else {
                            mView.showLoadToastError(e.getLocalizedMessage());
                        }
                        resultCallback.onError(e.getLocalizedMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    protected <T> void get(String url, Type type, ResultCallback<T> resultCallback) {
        get(url, type, null, null, resultCallback);
    }

    protected <T> void get(String url, Type type, HttpParams params, ResultCallback<T> resultCallback) {
        get(url, type, params, null, resultCallback);
    }

    public interface ResultCallback<T> {
        public void onSucceed(T t);

        public void onError(String e);
    }
}
