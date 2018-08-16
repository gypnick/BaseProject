package com.iwisedev.baseproject.mvp.p;

import com.google.gson.reflect.TypeToken;
import com.iwisedev.baseproject.base.BaseActivity;
import com.iwisedev.baseproject.base.BasePresenter;
import com.iwisedev.baseproject.mvp.c.MainActivityContract;
import com.iwisedev.baseproject.base.ResponseBean;
import com.iwisedev.baseproject.ui.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {


    public MainActivityPresenter(MainActivityContract.View view, BaseActivity baseActivity) {
        super(view, baseActivity);
    }

    @Override
    public void checkData() {
        Type type = new TypeToken<ResponseBean<List<Test>>>() {}.getType();
//        this.<ResponseBean<List<Test>>>getData("http://v3.wufazhuce.com:8000/api/banner/list/4",type)
//                .subscribe(new Observer<ResponseBean<List<Test>>>() {
//            @Override
//            public void onSubscribe( Disposable d) {
//                System.out.println("========onSubscribe====="+ Thread.currentThread().getName()+"====>");
//            }
//
//            @Override
//            public void onNext( ResponseBean<List<Test>> listResponseBean) {
//                System.out.println("========onNext====="+ Thread.currentThread().getName()+"=="+listResponseBean.getData().get(0).getTitle());
//                mView.showData(listResponseBean.getData());
//            }
//
//            @Override
//            public void onError( Throwable e) {
//                System.out.println("========onError====="+ Thread.currentThread().getName()+"==="+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("========onComplete====="+ Thread.currentThread().getName());
//            }
//        });


    }
}
