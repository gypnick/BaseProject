package site.site8.baseproject.mvp.p;

import com.google.gson.reflect.TypeToken;

import site.site8.baseproject.mvp.base.BaseActivity;
import site.site8.baseproject.mvp.base.BasePresenter;
import site.site8.baseproject.mvp.contract.MainActivityContract;
import site.site8.baseproject.mvp.m.ResponseBean;
import site.site8.baseproject.ui.Test;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.site8.baseproject.uitl.LogUtils;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {


    public MainActivityPresenter(MainActivityContract.View view, BaseActivity baseActivity) {
        super(view, baseActivity);
    }

    @Override
    public void checkData() {
        Type type = new TypeToken<ResponseBean<List<Test>>>() {
        }.getType();
        this.<ResponseBean<List<Test>>>get("http://v3.wufazhuce.com:8000/api/banner/list/4", type, new ResultCallback<ResponseBean<List<Test>>>() {
            @Override
            public void onSucceed(ResponseBean<List<Test>> listResponseBean) {
                LogUtils.sf("测试下载:" + listResponseBean.getData().size());
            }

            @Override
            public void onError(String e) {
                LogUtils.sf("测试失败:" + e);
            }
        });

    }
}
