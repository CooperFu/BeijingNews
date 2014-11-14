package io.naotou.beijingnews.pager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import io.naotou.beijingnews.MainActivity;
import io.naotou.beijingnews.R;
import io.naotou.beijingnews.base.BasePager;
import io.naotou.beijingnews.bean.NewCenter;
import io.naotou.beijingnews.utils.CooperApi;
import io.naotou.beijingnews.utils.SharedPreferencesUtils;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public class NewCenterPager extends BasePager {
    private static final String TAG = "NewCenterPager";
    private List<String> titleList = new ArrayList<String>();
    private List<BasePager> pagerList;
    private FrameLayout news_center_fl;


    public NewCenterPager(Context context) {

        super(context);
    }

    @Override
    public View initView() {

        view = View.inflate(context, R.layout.news_center_frame, null);
        news_center_fl = (FrameLayout) view.findViewById(R.id.news_center_fl);

        initTitleBar();
        return view;
    }

    @Override
    public void initData() {
        //先读取本地缓存数据, 如果有,就用本地的,刷新一下, 如果没有 再去网络获取.
        String result = SharedPreferencesUtils.getStringData(context, CooperApi.NEWS_CENTER_CATEGORIES, "");
        //如果不为空,就是本地有数据
        if (!TextUtils.isEmpty(result)) {
            getDataFromLocal(result);
        }
        //没有的话  在请求网络获取
        getDataFromNet();

    }

    private void getDataFromLocal(String result) {
        // 获取本地数据 去解析json
        Gson gson = new Gson();
        NewCenter newCenter = gson.fromJson(result, NewCenter.class);
        titleList.clear();
        for (int i = 0; i < newCenter.data.size(); i++) {
            titleList.add(newCenter.data.get(i).title);
        }
        ((MainActivity) context).getMenuFragment().initMenu(titleList);

        pagerList = new ArrayList<BasePager>();
        pagerList.clear();
        pagerList.add(new NewPager(context,newCenter.data.get(0)));
        pagerList.add(new TopicPager(context, newCenter.data.get(1)));
        pagerList.add(new PicPager(context, newCenter.data.get(2)));
        pagerList.add(new IntPager(context, newCenter.data.get(3)));

        switchPager(0);

    }

    public void switchPager(int i) {

        txt_title.setText(titleList.get(i));
        news_center_fl.removeAllViews();
        news_center_fl.addView(pagerList.get(i).getRootView());
        pagerList.get(i).initData();

    }


    public void getDataFromNet() {
        //这里是从网络获取数据
        loadData(HttpMethod.GET, CooperApi.NEWS_CENTER_CATEGORIES, null, new RequestCallBack<String>() {
            /**
             * @param responseInfo
             * 成功
             */
            public void onSuccess(ResponseInfo<String> responseInfo) {

                SharedPreferencesUtils.setStringData(context, CooperApi.NEWS_CENTER_CATEGORIES, responseInfo.result);
                Log.d(TAG, responseInfo.result);
                getDataFromLocal(responseInfo.result);

            }


            @Override
            public void onFailure(HttpException e, String s) {

                Log.d(TAG + "onFailure", s);
            }
        });
    }
}
