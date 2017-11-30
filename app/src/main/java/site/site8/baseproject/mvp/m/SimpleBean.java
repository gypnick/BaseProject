package site.site8.baseproject.mvp.m;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public class SimpleBean {
    private int res;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public ResponseBean toResponseBean() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setRes(res);
        return responseBean;
    }
}
