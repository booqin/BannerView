package com.xinguang.reactbanner.view;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * 一句话功能简述
 *
 * @author wl on 2017/9/11 0011 14:08
 */

public abstract class ImageCycleViewUtils<T> {
    List<T> list;

    public ImageCycleViewUtils() {
        this.list = getList();
    }


    public int getSize() {
        return list.size();
    }

    //
    public void displayImage(int position, ImageView imageView) {
        displayImage(list.get(position), imageView);
    }
    /**
     * 获得数据
     */
    public abstract List<T> getList();
    /**
     * 加载图片资源
     */
    public abstract void displayImage(T object, ImageView imageView);

    /**
     * 单击图片事件
     */
    public abstract void onImageClick(int position, View imageView);
    /**
     * 当前显示的页面
     */
    public void setCurrId(int currId){}
}
