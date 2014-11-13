package io.naotou.beijingnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.naotou.beijingnews.MainActivity;
import io.naotou.beijingnews.R;
import io.naotou.beijingnews.base.BaseFragment;
import io.naotou.beijingnews.base.MyBaseAdapter;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public class MenuFragment extends BaseFragment {

    private ListView lv_menu_news_center;
    private List<String> titleList;
    private MyAdapter adapter;
    private int currentPosition = 0;

    /**
     * @param inflater
     * @return 这里是左侧menu 应该动态从网络获取数据,根据点击的不同 获取不同的fragment.
     */
    @Override
    public View initView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.layout_left_menu, null);
        lv_menu_news_center = (ListView) view.findViewById(R.id.lv_menu_news_center);
        return view;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void initMenu(List<String> titleList) {

        this.titleList = titleList;
        if (adapter == null) {
            adapter = new MyAdapter(titleList, context);
            lv_menu_news_center.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        lv_menu_news_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentPosition = position;
                adapter.notifyDataSetChanged();

                //点击左侧条目的时候，右侧内容界面需要去做切换操作

                //1，newCenterPager对应对象去调用switchPager方法
                //2,newCenterPager?在何处创建，在HomeFragment对象中创建，
                //3，HomeFragment？在何处创建，在MainActivity
                ((MainActivity) context).getHomeFragment().getNewCenterPager().switchPager(position);
                slidingMenu.toggle();
            }
        });


    }


    private class MyAdapter extends MyBaseAdapter {

        public MyAdapter(List list, Context context) {

            super(list, context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.layout_item_menu, null);
                holder.iv_menu_item = (ImageView) convertView.findViewById(R.id.iv_menu_item);
                holder.tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_menu_item.setText(titleList.get(position));
            if (position == currentPosition) {
                holder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.red));
                holder.iv_menu_item.setImageResource(R.drawable.menu_arr_select);
                convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
            } else {
                holder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.white));
                holder.iv_menu_item.setImageResource(R.drawable.menu_arr_normal);
                convertView.setBackgroundResource(R.drawable.transparent);
            }
            return convertView;
        }
    }

    private class ViewHolder {

        public ImageView iv_menu_item;
        public TextView tv_menu_item;
    }
}
