package com.iwisedev.baseproject.base;

import android.content.Context;

/**
 * Created by Ledev2 on 2018-03-05.
 */

public class CommonPresenter extends CommonRecyclerViewPresenter {
    public CommonPresenter(CommonRecyclerViewContract.View view, Context context, String listUrl) {
        super(view, context, listUrl);
    }

    @Override
    protected boolean isPost() {
        return true;
    }
}
