package com.hqbb.yijiuqingren;

import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private List<ImageView> mImageList;

    private int previousPosition = 0;

    private boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        autoPlayView();
    }

    private void initData() {
        int[] imageResIDs = {
                R.mipmap.fengmian,
                R.mipmap.zfengmian,
                R.mipmap.t1,
                R.mipmap.t1t,
                R.mipmap.t2,
                R.mipmap.t2t,
                R.mipmap.t3,
                R.mipmap.t3t,
                R.mipmap.t4,
                R.mipmap.t4t,
                R.mipmap.t5,
                R.mipmap.t5t,
                R.mipmap.t6,
                R.mipmap.t6t,
                R.mipmap.t7,
                R.mipmap.t7t,
                R.mipmap.t8,
                R.mipmap.t8t,
                R.mipmap.t9,
                R.mipmap.t10,
        };
        mImageList = new ArrayList<>();
        ImageView iv;
        for (int i = 0; i < imageResIDs.length; i++) {
            iv = new ImageView(this);
            iv.setBackgroundResource(imageResIDs[i]);
            mImageList.add(iv);
        }
    }

    private void initView() {
        initData();
        mViewPager = this.findViewById(R.id.viewPager);
        PagerAdapter mAdapter = new PagerAdapter(mImageList, mViewPager);
        mViewPager.setAdapter(mAdapter);//第二步：设置viewpager适配器
        mViewPager.addOnPageChangeListener(this);
        setFirstLocation();
    }

    /**
     * 当ViewPager页面被选中时, 触发此方法.
     * @param position 当前被选中的页面的索引
     */
    @Override
    public void onPageSelected(int position) {
        //伪无限循环，滑到最后一张图片又重新进入第一张图片
        int newPosition = position % mImageList.size();

        // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
        previousPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 设置刚进入app时，显示的图片和文字
     */
    private void setFirstLocation() {
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % mImageList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        mViewPager.setCurrentItem(currentPosition);
    }

    /**
     * 每隔3秒自动播放图片
     */
    private void autoPlayView() {
        //自动播放图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    });
                    SystemClock.sleep(10000);
                }
            }
        }).start();
    }

    /**
     *当Activity销毁时取消图片自动播放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

}
