package io.naotou.beijingnews.pager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import io.naotou.beijingnews.base.BasePager;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public class SmartServicePager extends BasePager {
    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("智能");
        return textView;
    }

    @Override
    public void initData() {

    }
}
