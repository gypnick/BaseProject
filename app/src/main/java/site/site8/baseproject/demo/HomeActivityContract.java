package site.site8.baseproject.demo;


import site.site8.baseproject.mvp.base.BaseView;
import site.site8.baseproject.ui.Test;

import java.util.List;


/**
 * Created by Ledev2 on 2017-10-17.
 */

public interface HomeActivityContract {
    interface Presenter {
        void checkData();
    }
    interface View extends BaseView {
        void showError();
        void showData(List<Test> list);
    }
}
