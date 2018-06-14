package com.xinguang.reactbanner.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinguang.reactbanner.view.ImageCycleViewUtils;

import java.util.ArrayList;

/**
 * 使用getCount max来实现循环，不适用与React-Native，内存优化到导致在RNView中无法正常动态加载
 * Created by vitozhang on 2018/6/12.
 */

public class ImageCycleLoopMaxAdapter extends PagerAdapter {

    /**
     * 图片视图缓存列表
     */
    private ArrayList<ImageView> mImageViewCacheList;

    /**
     * 广告图片点击监听器
     */
    private ImageCycleViewUtils mImageCycleViewListener;

    private Context mContext;
    private boolean isManualLoop = true;

    public ImageCycleLoopMaxAdapter(Context context, ImageCycleViewUtils imageCycleViewListener) {
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
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = mImageViewCacheList.remove(0);
        }
        // 设置图片点击监听
        imageView.setOnClickListener(new View.OnClickListener() {
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
