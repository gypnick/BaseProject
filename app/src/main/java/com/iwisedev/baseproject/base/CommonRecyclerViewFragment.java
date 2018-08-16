package com.iwisedev.baseproject.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iwisedev.baseproject.R;
import com.iwisedev.baseproject.databinding.FragmentRecyclerCommonBinding;
import com.iwisedev.baseproject.uitl.UIUtils;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import site.site8.bannerview.BannerView;


/**
 * Created by Ledev2 on 2017-12-25.
 */

public abstract class CommonRecyclerViewFragment<D extends BaseBean, A extends BaseQuickAdapter, P extends CommonRecyclerViewPresenter> extends BaseFragment<CommonRecyclerViewContract.View, CommonRecyclerViewPresenter<D>> implements CommonRecyclerViewContract.View<D>, SpringView.OnFreshListener, BaseQuickAdapter.OnItemClickListener {
    protected RecyclerView recyclerView;
    protected SpringView springView;
    private static final int DEFAULT_PAGE = 0;
    protected int page = DEFAULT_PAGE;
    protected List<D> data;
    protected A adapter;
    protected P presenter;
    protected String adapterHeaderUrl;
    protected LinearLayout headLl;
    private FragmentRecyclerCommonBinding binding;

    @Override
    protected View initDataBindingViewId(LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_common, container, false);
        recyclerView = binding.recyclerView;
        springView = binding.springView;
        springView = binding.springView;
        headLl = binding.headLl;
        return binding.getRoot();
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        recyclerView.setLayoutManager(setLayoutManager());
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        data = new ArrayList<>();
        adapter = setAdapter();
        adapterHeaderUrl = setAdapterHeadViewUrl();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        springView.setHeader(new AliHeader(getContext(), true));
        if (canLoadMore()) springView.setFooter(new AliFooter(getContext(), true));
        springView.setListener(this);
        presenter = (P) mPresenter;

        setEmptyView();


    }

    /**
     * 获取传递过来的参数
     *
     * @return
     */
    public Bundle getArg() {
        return getArguments();
    }

    private void setEmptyView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view_layout, null, false);
        adapter.setEmptyView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        if (adapterHeaderUrl != null && adapterHeaderUrl.startsWith("http")) {
            mPresenter.getBannerData(adapterHeaderUrl);
        }
        onRefresh();
    }


    @Override
    public void setBannerData(List<BannerBean> list) {
        if (headLl != null) ;
        headLl.removeAllViews();
        headLl.addView(createHeadView(list));

    }


    /**
     * 数据加载完毕
     *
     * @param data
     */
    @Override
    public void setData(List<D> data) {
        springView.onFinishFreshAndLoad();
        if (this.data == null || data == null) return;
        if (page == DEFAULT_PAGE) {
            this.data.clear();
        }
        this.data.addAll(data);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void loadError(String e) {
        springView.onFinishFreshAndLoad();
        showLoadToastError(e);
    }

    @Override
    public void showLoadToastError(String e) {
        super.showLoadToastError(e);
        springView.onFinishFreshAndLoad();
    }

    public View createHeadView(List<BannerBean> list) {
        BannerView bannerView = (BannerView) LayoutInflater.from(getActivity()).inflate(R.layout.item_banner_view, null, false);
        ViewGroup.LayoutParams params = bannerView.getChildAt(0).getLayoutParams();
        params.height = UIUtils.dip2Px(147);
        params.width = -1;
        bannerView.getChildAt(0).setLayoutParams(params);
        ArrayList<String> urls = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            urls.add(list.get(i).getImage());
        }
        bannerView.create(urls, R.drawable.banner_default);
        bannerView.setBannerOnClickListener(new BannerView.BannerOnClickListener() {
            @Override
            public void onClick(int i) {
                if (null != list) {
                    //TODO 点击banner跳转
//                    BannerInfoActivity.nav2BannerInfoActivity(getActivity(), list.get(i).getBanner_article_id());
                }
            }
        });
        return bannerView;
    }


    @Override
    public void onRefresh() {
        page = DEFAULT_PAGE;
        mPresenter.getData(page);
        if (adapterHeaderUrl != null && adapterHeaderUrl.startsWith("http")) {
            mPresenter.getBannerData(adapterHeaderUrl);
        }
    }

    @Override
    public void onLoadmore() {
        page++;
        //这里分页参数奇葩，剩20
        mPresenter.getData(page * 20);
    }

    @Override
    protected P createPresenter() {
        return getPresenter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        itemClick(position);
    }


    public abstract void itemClick(int position);

    public abstract P getPresenter();

    /**
     * 设置布局管理器
     *
     * @return
     */
    protected abstract RecyclerView.LayoutManager setLayoutManager();

    /**
     * 设置adapter
     *
     * @return
     */
    protected abstract A setAdapter();

    /**
     * 是否可以加载更多
     *
     * @return
     */
    protected abstract boolean canLoadMore();

    /**
     * 设置adapter头部Url
     *
     * @return
     */
    protected abstract String setAdapterHeadViewUrl();


}
