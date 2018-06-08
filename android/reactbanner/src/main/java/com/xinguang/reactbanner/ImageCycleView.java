package com.xinguang.reactbanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;

/**
 * 广告图片自动轮播控件</br>
 * <p>
 * <pre>
 *   集合ViewPager和指示器的一个轮播控件，主要用于一般常见的广告图片轮播，具有自动轮播和手动轮播功能
 *   使用：只需在xml文件中使用{@code <com.minking.imagecycleview.ImageCycleView/>} ，
 *   然后在页面中调用  {@link #setImageResources(ImageCycleViewUtils) }即可!
 *
 *   另外提供{@link #startImageCycle() } \ {@link #pushImageCycle() }两种方法，用于在Activity不可见之时节省资源；
 *   因为自动轮播需要进行控制，有利于内存管理
 * </pre>
 *
 * @author minking
 */
public class ImageCycleView extends LinearLayout {
    private ImageCycleViewUtils imageCycleViewListener;
    /**
     * 是否显示指示器
     */
    private boolean isShow;
    private boolean isShow2;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;

    /**
     * 滚动图片视图适配器
     */
    private ImageCycleAdapter mAdvAdapter;

    /**
     * 图片轮播指示器控件
     */
    private ViewGroup mGroup;

    /**
     * 图片轮播指示器-个图
     */
    private View mLineView = null;

    /**
     * 滚动图片指示器-视图列表
     */
    private View[] mLineViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;

    /**
     * 设置是否手动无限循环
     */
    private boolean isManualLoop = false;

    private int size;
    /**
     * 滚动间隔
     */
    private int spacing = 3000;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        this(context,null);
    }

    /**
     * @param context
     * @param attrs
     */
    // 构造函数优先加载 所以添加到XML中可以执行
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ad_cycle_views, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视图
        mGroup = (ViewGroup) findViewById(R.id.viewGroup);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
//        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    /**
     * dip转pixel
     */
    public int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 装填图片数据
     */
    public void setImageResources(ImageCycleViewUtils imageCycleViewListener) {
        this.imageCycleViewListener = imageCycleViewListener;
        // 清除所有子视图
        mGroup.removeAllViews();
        // 图片广告数量
        // final int imageCount = imageUrlList.size();
        size = imageCycleViewListener.getSize();
        if (size <= 1) {
            isShow = false;
        } else {
            isShow = true;
        }
        mLineViews = new View[size];

        LayoutParams layout =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.width = dip2px(mContext, 16);
        layout.height = dip2px(mContext, 2);
        layout.setMargins(dip2px(mContext, (float) 2.5), 0,
                dip2px(mContext, (float) 2.5), 0);
        if (isShow) {
            // 图片指示器
            for (int i = 0; i < size; i++) {
                mLineView = LayoutInflater.from(mContext).inflate(R.layout.view_line, null);
                if (!isShow2) {
                    mLineView.setVisibility(GONE);
                }
                mLineView.setLayoutParams(layout);
                mLineViews[i] = mLineView;
                if (i == 0) {
                    mLineViews[i].setBackgroundResource(R.color.white);
                } else {
                    mLineViews[i].setBackgroundResource(R.color.pl_title_selected_no);
                }
                mGroup.addView(mLineViews[i]);
            }
        }
        mAdvAdapter = new ImageCycleAdapter(mContext, imageCycleViewListener);
        mAdvPager.setAdapter(mAdvAdapter);
        mAdvPager.setCurrentItem(size * 100000);// 设置手扒可轮回时打开

        startImageTimerTask();   //如果是第一个   使用start方法开始滚动
    }

    public void setIsManualLoop(boolean isLoop) {
        this.isManualLoop = isLoop;
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 开始图片滚动任务
     */
    public void startImageTimerTask() {
        if (size < 2) return;
        stopImageTimerTask();
        // 图片每3秒滚动一次
        mHandler.postDelayed(mImageTimerTask, spacing);
    }

    /**
     * 设置滚动间隔
     */
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    /**
     * 停止图片滚动任务
     */
    public void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            if (mLineViews != null) {
                mImageIndex++;
                if (!isManualLoop) {
                    // 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
                    if ((mImageIndex) == size) {
                        mImageIndex = 0;
                    }
                }
                mAdvPager.setCurrentItem(mImageIndex);
            }
        }
    };

    /**
     * 轮播图片状态监听器
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) startImageTimerTask(); // 开始下次计时
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            if (!isShow) return;
            // 设置当前显示的图片下标
            mImageIndex = index;
            int count = mLineViews.length;
            imageCycleViewListener.setCurrId(index % count);

            for (int i = 0; i < count; i++) {
                // 设置图片滚动指示器背景
                // 图片指示器
                if (index % count != i) {
                    mLineViews[i % count].setBackgroundResource(R.color.pl_title_selected_no);
                } else {
                    mLineViews[i % count].setBackgroundResource(R.color.white);
                }
            }
        }
    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<ImageView> mImageViewCacheList;

        /**
         * 广告图片点击监听器
         */
        private ImageCycleViewUtils mImageCycleViewListener;

        private Context mContext;

        public ImageCycleAdapter(Context context, ImageCycleViewUtils imageCycleViewListener) {
            mContext = context;

            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<ImageView>();
        }

        @Override
        public int getCount() {
            return isManualLoop ? Integer.MAX_VALUE
                    : mImageCycleViewListener.getSize();// 代表如果是循环播放的话数量就无法设定
        }

        private int getPosition(int position) {
            return position % mImageCycleViewListener.getSize();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(
                        new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(getPosition(position), v);
                }
            });

            //imageView.setTag(1,imageUrl);
            container.addView(imageView);
            mImageCycleViewListener.displayImage(getPosition(position), imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }
    }

    public void setShow2(boolean show2) {
        isShow2 = show2;
    }
    //==========================================
//    //判定为拖动的最小移动像素数
//    private int mTouchSlop;
//    //手指上次滑动的位置
//    private int mLastX;
//    private int mLastY;
//    //手指移动时所处的位置
//    private int mMoveX;
//    private int mMoveY;
//    @Override public boolean onInterceptTouchEvent(MotionEvent e) {
//
//        boolean consume = false;
//        int x = (int) e.getX();
//        int y = (int) e.getY();
//        switch (e.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                //获取手指按下时的坐标
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//
//                if (Math.abs(deltaX) > mTouchSlop || Math.abs(deltaY) > mTouchSlop) {
//                    //当y方向滑动距离小于x方向时，拦截事件，交给自己处理
//                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                        //不允许父元素拦截事件
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                        //此时拦截事件，直接调用onTouchEvent，传入onTouchEvent的ACTION_MOVE事件中，不再走onTouchEvent的ACTION_DOWN事件
//                        consume = true;
//                    } else {
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                    }
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//        return consume;
//    }
//
//    @Override public boolean onTouchEvent(MotionEvent e) {
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mMoveX = (int) e.getX();//3
//                mMoveY = (int) e.getY();
//                int deltaX = mMoveX - mLastX;
//                int deltaY = mMoveY - mLastY;
//
//                if (Math.abs(deltaX) > mTouchSlop || Math.abs(deltaY) > mTouchSlop) {
//                    //当y方向滑动距离小于x方向时，拦截事件，交给自己处理
//                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                        return true;
//                    }else{
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        return false;
//                    }
//                }
//
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return true;
//    }
}
