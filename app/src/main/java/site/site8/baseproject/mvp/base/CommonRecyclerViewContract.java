package site.site8.baseproject.mvp.base;

import android.os.Bundle;


import java.util.List;

import site.site8.baseproject.mvp.m.BannerBean;

/**
 * Created by Ledev2 on 2017-12-25.
 */

public interface  CommonRecyclerViewContract {
    interface Presenter{
        void getData(int page);
        void getBannerData(String bannerUrl);

    }
    interface View<D> extends BaseView{
       void setData(List<D> list);
        void loadError(String e);
        void setBannerData(List<BannerBean> list);
        Bundle getArg();
    }
}
