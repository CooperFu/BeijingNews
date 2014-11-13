package io.naotou.beijingnews.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import io.naotou.beijingnews.MainActivity;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public abstract class BaseFragment extends Fragment{

    public Context context;
    public SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        slidingMenu = ((MainActivity) context).getSlidingMenu();
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 构建ui
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }
    public abstract View initView(LayoutInflater inflater);

    protected abstract void initData(Bundle savedInstanceState);

}
