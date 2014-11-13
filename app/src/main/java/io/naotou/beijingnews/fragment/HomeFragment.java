package io.naotou.beijingnews.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


import java.util.ArrayList;

import io.naotou.beijingnews.R;
import io.naotou.beijingnews.base.BaseFragment;
import io.naotou.beijingnews.base.BasePager;
import io.naotou.beijingnews.pager.FunctionPager;
import io.naotou.beijingnews.pager.GovAffairsPager;
import io.naotou.beijingnews.pager.NewCenterPager;
import io.naotou.beijingnews.pager.SettingPager;
import io.naotou.beijingnews.pager.SmartServicePager;
import io.naotou.beijingnews.view.LazyViewPager;
import io.naotou.beijingnews.view.MyViewPager;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public class HomeFragment extends BaseFragment {

    private View view;
    private ArrayList<BasePager> basePagers;
    private MyViewPager layout_content;
    private RadioGroup radioGroup;



    @Override
    public View initView(LayoutInflater inflater) {
        view = View.inflate(context, R.layout.frag_home,null);
        layout_content = (MyViewPager) view.findViewById(R.id.layout_content);
        radioGroup = (RadioGroup) view.findViewById(R.id.main_radio);
        return view;
    }

    /**
     * @param savedInstanceState
     * 填充homeFragment中的数据
     */
    @Override
    protected void initData(Bundle savedInstanceState) {
        basePagers = new ArrayList<BasePager>();
        basePagers.add(new FunctionPager(getActivity()));
        basePagers.add(new NewCenterPager(getActivity()));
        basePagers.add(new SmartServicePager(getActivity()));
        basePagers.add(new GovAffairsPager(getActivity()));
        basePagers.add(new SettingPager(getActivity()));
        layout_content.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                basePagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layout_content.setAdapter(new MyPageAdapter());
        layout_content.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                basePagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_function:
                        //设置当前viewpager选中的界面
                        layout_content.setCurrentItem(0);
                        break;
                    case R.id.rb_news_center:
                        layout_content.setCurrentItem(1);
                        break;
                    case R.id.rb_smart_service:
                        layout_content.setCurrentItem(2);
                        break;
                    case R.id.rb_gov_affairs:
                        layout_content.setCurrentItem(3);
                        break;
                    case R.id.rb_setting:
                        layout_content.setCurrentItem(4);
                        break;
                }
            }

        });
        radioGroup.check(R.id.rb_function );
    }

    private class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(basePagers.get(position).getRootView());
            return basePagers.get(position).getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }

    }
    public NewCenterPager getNewCenterPager(){
        return (NewCenterPager) basePagers.get(1);
    }
}
