package com.iwisedev.baseproject.app;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.iwisedev.baseproject.base.ResponseBean;
import com.iwisedev.baseproject.widget.KyLoadingBuilder;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;

/**
 * Created by Ledev2 on 2017-10-09.
 */

public class JsonCallBack<T> extends AbsCallback<T> {
    private Type type;
    private Class<T> classzz;
    private KyLoadingBuilder loadingView;

    public JsonCallBack(Class<T> classzz, Activity activity) {
        this.classzz = classzz;
        initLoadingView(activity);
    }

    public JsonCallBack(Type type, Activity activity) {
        this.type = type;
        initLoadingView(activity);
    }

    /**
     * 显示等待提示框
     */
    public void initLoadingView(Activity activity) {
        loadingView = new KyLoadingBuilder(activity);
        loadingView.setCornerRadius(20);
        loadingView.setText("加载中。。。");
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());


        Type genType = getClass().getGenericSuperclass();
        type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        Type rawType = ((ParameterizedType) type).getRawType();                     // 泛型的实际类型
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0]; // 泛型的参数

        if (rawType != ResponseBean.class) {
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            T t = gson.fromJson(jsonReader, type);
            response.close();
            return t;
        } else {
//            if (typeArgument == Void.class) {
//                // 泛型格式如下： new JsonCallback<LzyResponse<Void>>(this)
//                SimpleBean simpleResponse = Convert.fromJson(jsonReader, SimpleBean.class);
//                response.close();
//                //noinspection unchecked
//                return (T) simpleResponse.toLzyResponse();
//            } else {
            // 泛型格式如下： new JsonCallback<LzyResponse<内层JavaBean>>(this)
            ResponseBean lzyResponse = gson.fromJson(jsonReader, type);
            response.close();
//                int code = lzyResponse.code;
//                //这里的0是以下意思
//                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
//                if (code == 0) {
//                    //noinspection unchecked
//                    return (T) lzyResponse;
//                } else if (code == 104) {
//                    throw new IllegalStateException("用户授权信息无效");
//                } else if (code == 105) {
//                    throw new IllegalStateException("用户收取信息已过期");
//                } else {
//                    //直接将服务端的错误信息抛出，onError中可以获取
//                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyResponse.msg);
//                }
            return (T) lzyResponse;
//            }
        }
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (loadingView != null) loadingView.show();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (loadingView != null) {
            loadingView.dismiss();
            loadingView = null;
        }
    }

    @Override
    public void onSuccess(Response<T> response) {

    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        if(exception instanceof UnknownHostException){
            System.out.println("=======没有网络========");
        }else if(exception instanceof SocketTimeoutException){
            System.out.println("=======网络连接超时========");
        }else if(exception instanceof IllegalStateException){
            String message = exception.getMessage();
            System.out.println("=======message========="+message);
        }
    }
}
