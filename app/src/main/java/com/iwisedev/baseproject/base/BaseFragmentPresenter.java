package com.iwisedev.baseproject.base;

import android.content.Context;


import com.iwisedev.baseproject.config.JsonConvert;
import com.iwisedev.baseproject.uitl.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseFragmentPresenter<V extends BaseView> {

    public V mView;
    public Context mContext;

    public BaseFragmentPresenter(V view, BaseActivity baseActivity) {
        mView = view;
        mContext = baseActivity;
    }

/*================== 以下是网络请求接口 ==================*/
//Type type = new TypeToken<ResponseBean<ServerModel>>() {}.getType();

    public <T> Observable<T> getData(String url,Type type) {
        return getData(url,type,null,null);
    }
    public <T> Observable<T> getData(String url, Type type, HttpHeaders httpHeaders, HttpParams params) {
        Observable observable = OkGo.<T>get(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> mView.showWaitingDialog())
                .doFinally(() -> mView.hideWaitingDialog())
                .compose(mView.bindLifecycle());
        return observable;
    }
    public <T> Observable<T> postData(String url, Type type, HttpHeaders httpHeaders, HttpParams params) {
        Observable observable = OkGo.<T>get(url)
                .params(params)
                .headers(httpHeaders)
                .converter(new JsonConvert<T>(type) {
                })
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())//

                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((m) -> mView.showWaitingDialog())
                .doFinally(() -> mView.hideWaitingDialog())
                .compose(mView.bindLifecycle());
        return observable;
    }

    public <T> Observable<T> postData(String url,Type type) {
        return postData(url,type,null,null);
    }

    public <T> Observable<T> postDataByToken(String url,Type type) {
        HttpParams httpParams = new HttpParams();
        return postData(url,type,null,httpParams);
    }


}
