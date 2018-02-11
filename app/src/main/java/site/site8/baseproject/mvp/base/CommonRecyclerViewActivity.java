package site.site8.baseproject.mvp.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import site.site8.bannerview.BannerView;
import site.site8.baseproject.R;
import site.site8.baseproject.mvp.m.BannerBean;
import site.site8.baseproject.uitl.UIUtils;

/**
 * Created by Ledev2 on 2017-12-25.
 */

public abstract class CommonRecyclerViewActivity<D extends BaseBean,A extends BaseQuickAdapter,P extends CommonRecyclerViewPresenter> extends BaseActivity<CommonRecyclerViewContract.View, CommonRecyclerViewPresenter<D>> implements CommonRecyclerViewContract.View<D>, SpringView.OnFreshListener, BaseQuickAdapter.OnItemClickListener {
    @Bind(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @Bind(R.id.springView)
    protected  SpringView springView;
    private static final int DEFAULT_PAGE=0;
    protected int page = DEFAULT_PAGE;

    protected List<D> data;
    protected A adapter;
    protected P presenter;
    protected String adapterHeaderUrl;
    @Bind(R.id.head_ll)
    LinearLayout headLl;
    @Override
    protected int provideContentViewId() {
        return R.layout.layout_recycler__activity_view;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(setLayoutManager());
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        data = new ArrayList<>();
        adapter = setAdapter();
        adapterHeaderUrl = setAdapterHeadViewUrl();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        springView.setHeader(new AliHeader(this, true));
        if (canLoadMore()) springView.setFooter(new AliFooter(this, true));
        springView.setListener(this);
        presenter = (P) mPresenter;

        //没有头部banner就才设置空布局
        if (adapterHeaderUrl == null) {
            setEmptyView();
        }

    }

    private void setEmptyView() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_view_layout, null, false);
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
        if (page ==DEFAULT_PAGE) {
            this.data.clear();
        }
        springView.onFinishFreshAndLoad();
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
        BannerView bannerView = (BannerView) LayoutInflater.from(this).inflate(R.layout.item_banner_view, null, false);
        ViewGroup.LayoutParams params = bannerView.getChildAt(0).getLayoutParams();
        params.height = UIUtils.dip2Px( 147);
        params.width = -1;
        bannerView.getChildAt(0).setLayoutParams(params);
        ArrayList<String> urls = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            //TODO
//            urls.add(UrlConstants.IMG_URL_HEAD + list.get(i).getImage());
        }
        //TODO
//        bannerView.create(urls, R.drawable.list_item_default);
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
        mPresenter.getData(page);
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
     * @return
     */
    protected abstract RecyclerView.LayoutManager setLayoutManager();

    /**
     * 设置adapter
     * @return
     */
    protected abstract A setAdapter();

    /**
     * 是否可以加载更多
     * @return
     */
    protected abstract boolean canLoadMore();
    /**
     * 设置adapter头部Url
     * @return
     */
    protected abstract String setAdapterHeadViewUrl();
}
