package io.naotou.beijingnews.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.naotou.beijingnews.R;
import io.naotou.beijingnews.base.BasePager;
import io.naotou.beijingnews.bean.NewCenter;
import io.naotou.beijingnews.view.pagerindicator.TabPageIndicator;

/**
 * Created by Jack_Cooper on 2014/11/13.
 * Have a nice day!
 */
public class NewPager extends BasePager {

    private final NewCenter.NewCenterItem newCenterItem;
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;

    @ViewInject(R.id.pager)
    private ViewPager pager;
    private List<BasePager> pagerList;
    private MyPageAdapter adapter;

    public NewPager(Context context, NewCenter.NewCenterItem newCenterItem) {

        super(context);
        this.newCenterItem = newCenterItem;
    }

    @Override
    public View initView() {

        view = View.inflate(context, R.layout.frag_news, null);
        ViewUtils.inject(this, view);
        return view;

    }

    @Override
    public void initData() {

        pagerList = new ArrayList<BasePager>();
        pagerList.clear();
        for (int i = 0; i < newCenterItem.children.size(); i++) {
            pagerList.add(new NewCenterItemPager(context, newCenterItem.children.get(i).url));
        }
        if (adapter == null) {
            adapter = new MyPageAdapter();
            pager.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerList.get(position).initData();
                indicator.setCurrentItem(position);
                if (position != 0) {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                } else {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerList.get(0).initData();
    }

    private class MyPageAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {

            return newCenterItem.children.get(position).title;
        }

        @Override
        public int getCount() {

            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(pagerList.get(position).getRootView());
            return pagerList.get(position).getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
