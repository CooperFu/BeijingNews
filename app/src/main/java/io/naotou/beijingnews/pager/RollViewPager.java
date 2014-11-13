package io.naotou.beijingnews.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    class RunnableTask implements Runnable{

        @Override
        public void run() {
            //维护让图片一致滚动的操作
            currentPosition = (currentPosition+1)%imageUrlList.size();
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler(){
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
        handler.postDelayed(runnableTask,3000);

    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {

            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(getContext(), R.layout.viewpager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            bitmapUtils.display(imageView,imageUrlList.get(position));
            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
