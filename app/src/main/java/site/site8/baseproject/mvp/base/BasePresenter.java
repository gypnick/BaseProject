package site.site8.baseproject.mvp.base;

import android.content.Context;

import site.site8.baseproject.config.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends BaseView> {

    public V mView;
    public Context mContext;

    public BasePresenter(V view, BaseActivity baseActivity) {
        mView = view;
        mContext = baseActivity;
    }

    public BasePresenter(V view) {
        mView = view;
    }
/*================== 以下是网络请求接口 ==================*/
//Type type = new TypeToken<LzyResponse<ServerModel>>() {}.getType();

    public <T> Observable<T> getData(String url,Type type) {
        return getData(url,type,null,null);
    }
    public <T> Observable<T> getData(String url, Type type, HttpHeaders httpHeaders, HttpParams params) {
        Observable<T> observable = OkGo.<T>get(url)
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
        Observable<T> observable = OkGo.<T>get(url)
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


}
