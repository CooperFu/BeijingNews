package io.naotou.beijingnews;

import android.os.Bundle;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import io.naotou.beijingnews.fragment.HomeFragment;
import io.naotou.beijingnews.fragment.MenuFragment;


public class MainActivity extends SlidingActivity {

    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content);
        setBehindContentView(R.layout.frame);
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        //动态获取屏幕的宽的一半设置给侧滑菜单
        int widthPixels = getResources().getDisplayMetrics().widthPixels / 2;
        slidingMenu.setBehindOffset(widthPixels);
        //设置滑动范围是全局.
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        MenuFragment menuFragment = new MenuFragment();
        getFragmentManager().beginTransaction().replace(R.id.menu, menuFragment, "MENU").commit();
        HomeFragment homeFragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment, "HOME").commit();

    }

    public MenuFragment getMenuFragment() {
        return (MenuFragment) getFragmentManager().findFragmentByTag("MENU");
    }
    public HomeFragment getHomeFragment(){
        return (HomeFragment) getFragmentManager().findFragmentByTag("HOME");
    }
}
