package com.iwisedev.baseproject.base;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;


import com.lzy.okgo.model.HttpParams;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        if (url == null) return;

        //TODO 测试用
//        if (url.isEmpty()) {
//            List<LoginFragmentBean> data = new ArrayList<>();
//            for (int i = 0; i < 11; i++) {
//                data.add(new LoginFragmentBean());
//            }
//            mView.setData(data);
//            return;
//        }
        HttpParams httpParams = otherHttpParams(page);
        if (httpParams == null) {
            httpParams = getHttpParams();
        }
        httpParams.put("page", page);
        //设置Token TODO
//        httpParams.put("user_token", SPUtils.getUserToken().trim().isEmpty() ? null : SPUtils.getUserToken().trim());


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
            this.<ResponseBean<List<D>>>post(url, getBeanType(), httpParams, needLoading(), new ResultCallback<ResponseBean<List<D>>>() {
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
        HttpParams httpParams = getHttpParams();
        httpParams.put("banner_id", 2);
        this.<ResponseBean<List<BannerBean>>>postNoLoading(bannerUrl, type, httpParams, new ResultCallback<ResponseBean<List<BannerBean>>>() {
            @Override
            public void onSucceed(ResponseBean<List<BannerBean>> listResponseBean) {
                mView.setBannerData(listResponseBean.getData());
            }

            @Override
            public void onError(String e) {

            }
        });


    }

    protected boolean isPost() {
        return true;
    }

    /**
     * 是否需要loadingView
     *
     * @return
     */
    protected boolean needLoading() {
        return true;
    }

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
