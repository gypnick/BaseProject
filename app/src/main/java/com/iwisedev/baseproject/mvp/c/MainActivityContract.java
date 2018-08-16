package com.iwisedev.baseproject.mvp.c;

import com.iwisedev.baseproject.base.BaseView;
import com.iwisedev.baseproject.ui.Test;

import java.util.List;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public interface MainActivityContract {
    interface Presenter {
        void checkData();
    }

    interface View extends BaseView {
        void showError();

        void showData(List<Test> list);
    }
}
