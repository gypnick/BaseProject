package site.site8.baseproject.mvp.m;

/**
 * Created by Ledev2 on 2017-10-09.
 */

public class ResponseBean<T> {
    private int res;
    private T data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "res=" + res +
                ", data=" + data +
                '}';
    }
}
