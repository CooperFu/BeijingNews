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
public class SettingPager extends BasePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("设置");
        return textView;
    }

    @Override
    public void initData() {

    }
}
