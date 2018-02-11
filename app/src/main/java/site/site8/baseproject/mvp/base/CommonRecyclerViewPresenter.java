package site.site8.baseproject.mvp.base;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;

import com.lzy.okgo.model.HttpParams;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import site.site8.baseproject.app.MyApp;
import site.site8.baseproject.mvp.m.BannerBean;
import site.site8.baseproject.mvp.m.ResponseBean;
import site.site8.baseproject.uitl.SPUtils;

/**
 * Created by Ledev2 on 2017-12-25.
 */

public abstract class CommonRecyclerViewPresenter<D extends BaseBean> extends BasePresenter<CommonRecyclerViewContract.View> implements CommonRecyclerViewContract.Presenter {
    private String url;

    public CommonRecyclerViewPresenter(CommonRecyclerViewContract.View view, Context context, String listUrl) {
        super(view, context);
        url = listUrl;
    }

    @Override
    public void getData(int page) {

        if (url.isEmpty()) {
            List<BaseBean> data = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                data.add(new BaseBean());
            }
            mView.setData(data);
            return;
        }
        HttpParams httpParams = otherHttpParams(page);
        if (httpParams == null) {
            httpParams = getHttpParams();
            httpParams.put("page", page);
//            httpParams.put("user_token", SPUtils.getUserToken().trim().isEmpty() ? null : SPUtils.getUserToken().trim());
        }

        if (!isPost()) {
            this.<ResponseBean<List<D>>>get(url, getBeanType(), httpParams, new ResultCallback<ResponseBean<List<D>>>() {
                @Override
                public void onSucceed(ResponseBean<List<D>> listResponseBean) {
                    mView.setData(listResponseBean.getData());
                }

                @Override
                public void onError(String e) {
                    if (e.equals("请求无数据")) {
                        mView.setData(new ArrayList());
                    }
                }
            });

        } else if (isPost()) {
//            Type type = new TypeToken<ResponseBean<List<ExpertLiveInfoBean>>>() {}.getType();
            this.<ResponseBean<List<D>>>postNoLoading(url, getBeanType(), httpParams, new ResultCallback<ResponseBean<List<D>>>() {
                @Override
                public void onSucceed(ResponseBean<List<D>> listResponseBean) {
                    mView.setData(listResponseBean.getData());

                }

                @Override
                public void onError(String e) {
                    if (e.equals("请求无数据")) {
                        mView.setData(new ArrayList());
                    }
                }
            });
        } else {
            mView.loadError("加载错误");
        }

    }

    /**
     * 获取传递过来的参数
     *
     * @return
     */
    public Bundle getArg() {
        return mView.getArg();
    }

    @Override
    public void getBannerData(String bannerUrl) {
        Type type = new TypeToken<ResponseBean<List<BannerBean>>>() {
        }.getType();
        this.<ResponseBean<List<BannerBean>>>postNoLoading(bannerUrl, type, new ResultCallback<ResponseBean<List<BannerBean>>>() {
            @Override
            public void onSucceed(ResponseBean<List<BannerBean>> listResponseBean) {
                mView.setBannerData(listResponseBean.getData());
            }

            @Override
            public void onError(String e) {

            }
        });


    }

    protected abstract boolean isPost();

    protected Type getBeanType() {


        return new TypeToken<String>() {
        }.getType();
    }

    /**
     * 特殊的请求头
     *
     * @return
     */
    protected HttpParams otherHttpParams(int page) {
        return null;
    }


}
