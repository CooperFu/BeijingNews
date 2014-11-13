package io.naotou.beijingnews.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import io.naotou.beijingnews.base.BasePager;
import io.naotou.beijingnews.bean.NewCenter;

/**
 * Created by Jack_Cooper on 2014/11/13.
 * Have a nice day!
 */
public class IntPager extends BasePager {
    public IntPager(Context context, NewCenter.NewCenterItem newCenterItem) {

        super(context);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(context);
        textView.setText("int");
        return textView;
    }

    @Override
    public void initData() {

    }
}
