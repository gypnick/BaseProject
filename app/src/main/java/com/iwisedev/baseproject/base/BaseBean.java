package com.iwisedev.baseproject.base;

import java.io.Serializable;

/**
 * Created by Ledev2 on 2017-12-25.
 */

public class BaseBean implements Serializable{
    private String className=getClass().getSimpleName();

    public String getClassName() {
        return className;
    }

}
