package io.naotou.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import io.naotou.beijingnews.MainActivity;
import io.naotou.beijingnews.R;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public abstract class BasePager {
    public Context context;
    public View view;
    public SlidingMenu slidingMenu;

    public TextView txt_title;

    public BasePager(Context context) {

        this.context = context;
        view = initView();
        slidingMenu = ((MainActivity)context).getSlidingMenu();
    }

    public abstract View initView();

    public abstract void initData();

    public View getRootView() {

        return view;
    }

    public void loadData(HttpMethod httpMethod, String url, RequestParams params, RequestCallBack back) {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(httpMethod, url, params, back);
    }

    public void initTitleBar() {
        Button button = (Button) view.findViewById(R.id.btn_left);
        button.setVisibility(View.GONE);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imgbtn_left);
        imageButton.setImageResource(R.drawable.img_menu);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "测试按钮", Toast.LENGTH_SHORT).show();
            }
        });
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.VISIBLE);

        ImageButton imgbtn_text = (ImageButton) view.findViewById(R.id.imgbtn_text);
        imgbtn_text.setVisibility(View.GONE);

        ImageButton imgbtn_right = (ImageButton) view.findViewById(R.id.imgbtn_right);
        imgbtn_right.setVisibility(View.GONE);

        ImageButton btn_right = (ImageButton) view.findViewById(R.id.btn_right);
        btn_right.setVisibility(View.GONE);

    }
}
