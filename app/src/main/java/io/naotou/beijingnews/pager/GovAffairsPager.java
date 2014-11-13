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
public class GovAffairsPager extends BasePager {
    public GovAffairsPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("政府");
        return textView;
    }

    @Override
    public void initData() {

    }
}
