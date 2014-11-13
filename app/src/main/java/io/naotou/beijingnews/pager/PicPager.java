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
public class PicPager extends BasePager {
    public PicPager(Context context, NewCenter.NewCenterItem newCenterItem) {

        super(context);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(context);
        textView.setText("图片");
        return textView;
    }

    @Override
    public void initData() {

    }
}
