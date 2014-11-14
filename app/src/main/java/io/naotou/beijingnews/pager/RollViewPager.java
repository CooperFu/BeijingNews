package io.naotou.beijingnews.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import io.naotou.beijingnews.R;

/**
 * Created by Jack_Cooper on 2014/11/14.
 * Have a nice day!
 */
public class RollViewPager extends ViewPager {
    private TextView top_news_title;
    private List<String> titleList;
    private List<String> imageUrlList;
    private List<View> dotList;
    private BitmapUtils bitmapUtils;
    private MyAdapter adapter;
    private RunnableTask runnableTask;
    private int currentPosition = 0;
    private int downX;
    private int downY;
    private int moveX;
    private int moveY;

    private class RunnableTask implements Runnable {

        @Override
        public void run() {
            //维护让图片一致滚动的操作
            currentPosition = (currentPosition + 1) % imageUrlList.size();

            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            RollViewPager.this.setCurrentItem(currentPosition);
            //无限循环
            startRoll();
        }
    };

    public RollViewPager(Context context, final List<View> dotList) {

        super(context);
        this.dotList = dotList;
        bitmapUtils = new BitmapUtils(context);
        runnableTask = new RunnableTask();
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                top_news_title.setText(titleList.get(position));
                for (int i = 0; i < imageUrlList.size(); i++) {
                    if (i == position) {
                        dotList.get(i).setBackgroundResource(R.drawable.dot_focus);
                    } else {
                        dotList.get(i).setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public RollViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    public void initTitle(List<String> titleList, TextView top_news_title) {

        this.top_news_title = top_news_title;
        this.titleList = titleList;
        if (titleList != null && top_news_title != null && titleList.size() > 0) {
            //第一次进来的时候.
            top_news_title.setText(titleList.get(0));
        }


    }

    /**
     * 这里是删除当前handler中维护的所有任务和消息.目的是防止点 乱跳.
     */
    @Override
    protected void onDetachedFromWindow() {

        handler.removeCallbacksAndMessages(null);

        super.onDetachedFromWindow();
    }

    public void initImage(List<String> imageUrlList) {

        this.imageUrlList = imageUrlList;

    }

    public void startRoll() {

        if (adapter == null) {
            adapter = new MyAdapter();
            this.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        //让图片滑动
        handler.postDelayed(runnableTask, 3000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下的时候.
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) ev.getX();
                moveY = (int) ev.getY();
                if (Math.abs(moveY - downY) < Math.abs(moveX - downX)) {
                    //横向滑动.
                    //不要打扰我滑动.
                    handler.removeCallbacksAndMessages(null);
                    if (moveX - downX > 0 && getCurrentItem() == 0) {
                        //说明在第一个的时候向左划
                        getParent().requestDisallowInterceptTouchEvent(false);//交给系统处理
                    } else if (moveX - downX > 0 && getCurrentItem() < getAdapter().getCount() - 1) {
                        //说明是向左划并且当前的点在自己需要处理的位置上
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else if (moveX - downX < 0 && getCurrentItem() == getAdapter().getCount() - 1) {
                        //说明在最后一个向右滑动 就进入下一个
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (moveX - downX < 0 && getCurrentItem() < getAdapter().getCount() - 1) {
                        //说明向右滑动,并且是想自己处理的地方
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }


                } else {
                    //竖向滑动. 下拉刷新的逻辑应该在这里.
                }
                break;
            case MotionEvent.ACTION_UP:
                //当抬起的时候,继续轮播
                currentPosition = getCurrentItem();
                startRoll();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {

            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(getContext(), R.layout.viewpager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            bitmapUtils.display(imageView, imageUrlList.get(position));
            container.addView(view);
            view.setOnTouchListener(new OnTouchListener() {

                private long downTime;
                private long upTime;
                private int upY;
                private int upX;
                private int downY;
                private int downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downX = (int) event.getX();
                            downY = (int) event.getY();
                            downTime = System.currentTimeMillis();
                            //按下的时候 让所有的滑动都结束
                            handler.removeCallbacksAndMessages(null);
                            break;

                        case MotionEvent.ACTION_UP:
                            upX = (int) event.getX();
                            upY = (int) event.getY();
                            upTime = System.currentTimeMillis();
                            if (upTime - downTime < 2000&&upX==downX) {
                                //短按
                                Toast.makeText(getContext(),"没按超过两秒",Toast.LENGTH_SHORT).show();
                            }else if (upTime - downTime > 2000&&downX==upX) {
                                //长按
                                Toast.makeText(getContext(),"长按超过两秒",Toast.LENGTH_SHORT).show();
                            }
                            startRoll();

                            break;
                        case MotionEvent.ACTION_CANCEL:
                            //如果取消的话, 继续当前轮播图
                            currentPosition = getCurrentItem() ;
                            startRoll();
                            break;
                    }


                    return true;
                }
            });
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }
}
