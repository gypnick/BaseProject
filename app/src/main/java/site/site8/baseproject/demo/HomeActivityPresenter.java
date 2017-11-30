package site.site8.baseproject.demo;


import com.google.gson.reflect.TypeToken;
import site.site8.baseproject.mvp.base.BasePresenter;
import site.site8.baseproject.mvp.m.ResponseBean;
import site.site8.baseproject.ui.Test;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ledev2 on 2017-10-17.
 */

public class HomeActivityPresenter extends BasePresenter<HomeActivityContract.View> implements HomeActivityContract.Presenter {


    public HomeActivityPresenter(HomeActivityContract.View view) {
        super(view);
    }

    @Override
    public void checkData() {
        Type type = new TypeToken<ResponseBean<List<Test>>>() {}.getType();
        this.<ResponseBean<List<Test>>>getData("http://v3.wufazhuce.com:8000/api/banner/list/4",type)
                .subscribe(new Observer<ResponseBean<List<Test>>>() {
                    @Override
                    public void onSubscribe( Disposable d) {
                        System.out.println("========onSubscribe====="+ Thread.currentThread().getName()+"====>");
                    }

                    @Override
                    public void onNext( ResponseBean<List<Test>> listResponseBean) {
                        System.out.println("========onNext====="+ Thread.currentThread().getName()+"=="+listResponseBean.getData().get(0).getTitle());
                        mView.showData(listResponseBean.getData());
                    }

                    @Override
                    public void onError( Throwable e) {
                        System.out.println("========onError====="+ Thread.currentThread().getName()+"==="+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("========onComplete====="+ Thread.currentThread().getName());
                    }
                });


    }


}
