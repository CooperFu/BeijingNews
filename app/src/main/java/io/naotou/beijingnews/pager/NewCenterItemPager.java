package io.naotou.beijingnews.pager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.naotou.beijingnews.R;
import io.naotou.beijingnews.base.BasePager;
import io.naotou.beijingnews.bean.NewBean;
import io.naotou.beijingnews.utils.CommonUtil;
import io.naotou.beijingnews.utils.CooperApi;
import io.naotou.beijingnews.utils.SharedPreferencesUtils;

/**
 * Created by Jack_Cooper on 2014/11/13.
 * Have a nice day!
 */
public class NewCenterItemPager extends BasePager {


    private String url;
    private View view;
    //放置轮播图片的LinearLayout
    @ViewInject(R.id.top_news_viewpager)
    private LinearLayout top_news_viewpager;
    //放置图片标题
    @ViewInject(R.id.top_news_title)
    private TextView top_news_title;
    //放置轮播点的线性布局
    @ViewInject(R.id.dots_ll)
    private LinearLayout dots_ll;

    @ViewInject(R.id.lv_item_news)
    private ListView lv_item_news;

    private List<String> imageUrlList = new ArrayList<String>();
    private List<String> titleList = new ArrayList<String>();
    private List<View> dotList = new ArrayList<View>();


    public NewCenterItemPager(Context context, String url) {

        super(context);
        this.url = url;
    }

    @Override
    public View initView() {

        View inflate = View.inflate(context, R.layout.layout_roll_view, null);
        ViewUtils.inject(this, inflate);
        view = View.inflate(context, R.layout.frag_item_news, null);
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void initData() {

        String result = SharedPreferencesUtils.getStringData(context, CooperApi.BASE_URL + url, "");
        if (!TextUtils.isEmpty(result)) {
            processData(result);
        }
        getData();
    }

    private void getData() {

        loadData(HttpRequest.HttpMethod.GET, CooperApi.BASE_URL + url, null, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                SharedPreferencesUtils.setStringData(context, CooperApi.BASE_URL + url, responseInfo.result);
                processData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }

    private void processData(String result) {

        NewBean newBean = new Gson().fromJson(result, NewBean.class);
        imageUrlList.clear();
        titleList.clear();
        for (int i = 0; i < newBean.data.topnews.size(); i++) {
            //这里要获取轮播图需要用到的几样东西.
            String title = newBean.data.topnews.get(i).title;
            titleList.add(title);
            String topImage = newBean.data.topnews.get(i).topimage;
            imageUrlList.add(topImage);
        }
        //这里应该让点进行初始化
        initDot();

    }

    private void initDot() {

        dots_ll.removeAllViews();
        dotList.clear();
        for (int i = 0; i < imageUrlList.size(); i++) {
            View view = new View(context);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.dot_focus);
            } else {
                view.setBackgroundResource(R.drawable.dot_normal);
            }
            //现在点有了,设置点的大小
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    CommonUtil.dip2px(context, 6),
                    CommonUtil.dip2px(context, 6));
            layoutParams.setMargins
                    (CommonUtil.dip2px(context,4),0
                    ,CommonUtil.dip2px(context,4),0);

            dots_ll.addView(view);
            dotList.add(view);

        }

    }
}
