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
 * 实现了count+2模式循环的Adapter
 * Created by vitozhang on 2018/6/12.
 */

public class ImageCycleLoop2Adapter extends PagerAdapter{

    /**
     * 图片视图缓存列表
     */
    private ArrayList<ImageView> mImageViewCacheList;

    /**
     * 广告图片点击监听器
     */
    private ImageCycleViewUtils mImageCycleViewListener;

    private Context mContext;

    public ImageCycleLoop2Adapter(Context context, ImageCycleViewUtils imageCycleViewListener) {
        mContext = context;

        mImageCycleViewListener = imageCycleViewListener;
        mImageViewCacheList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImageCycleViewListener.getSize()<=1?mImageCycleViewListener.getSize():mImageCycleViewListener.getSize()+2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getPosition(int position){
        int index = position;
        if (getCount()>1) {
            //循环导致位置向后偏移1，尾部新增1
            index = position == 0? mImageCycleViewListener.getSize()-1: (position==mImageCycleViewListener.getSize()+1?0:position - 1);
        }
        return index;
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

        container.addView(imageView);
        mImageCycleViewListener.displayImage(getPosition(position), imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
        mImageViewCacheList.add(view);
    }}
