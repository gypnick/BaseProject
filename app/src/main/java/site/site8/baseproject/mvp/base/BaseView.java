package site.site8.baseproject.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public interface BaseView {


    /**
     * 显示等待提示
     */
     void showWaitingDialog();
    /**
     * 隐藏等待提示
     */
    void hideWaitingDialog();
    <T>LifecycleTransformer <T>bindLifecycle();

    /**
     * 网络加载错误
     * @param eMsg
     */
    void showLoadToastError(String eMsg);

}
