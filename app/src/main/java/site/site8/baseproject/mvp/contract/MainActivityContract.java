package site.site8.baseproject.mvp.contract;

import site.site8.baseproject.mvp.base.BaseView;
import site.site8.baseproject.ui.Test;

import java.util.List;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public interface MainActivityContract {
    interface Presenter{
        void checkData();
    }
    interface View extends BaseView{
        void showError();
        void showData(List<Test> list);
    }
}
