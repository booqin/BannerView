package com.xinguang.reactbanner.react;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.xinguang.reactbanner.ImageCycleView;
import com.xinguang.reactbanner.ImageCycleViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vitozhang on 2018/6/8.
 */

public class ReactBannerManager extends SimpleViewManager<ImageCycleView>{

    private static final String NAME = "BannerView";


    private String[] strings = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518226&di=b0534ce7158ca8e51d3a199d934bdce1&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e8a157f86d8ca84a0d304fcb9943.jpg%402o.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449646201&di=f87bfff31d6c2500e7a312f465d8edfe&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F287%2F32%2F1E2205O0EVSB_1000x500.jpg" };


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ImageCycleView createViewInstance(ThemedReactContext reactContext) {
        ImageCycleView imageCycleView = new ImageCycleView(reactContext);
        return imageCycleView;
    }

    @ReactProp(name = "dataSet")
    public void setDataSet(final ImageCycleView imageCycleView, final ReadableArray array){
        ImageCycleViewUtils imageCycleViewUtils = new ImageCycleViewUtils() {
            @Override
            public List getList() {
                return Arrays.asList(strings);
            }

            @Override
            public void displayImage(Object object, ImageView imageView) {
                if (object instanceof String) {
                    Glide.with(imageCycleView).load((String)object).into(imageView);
                }
            }

            @Override
            public void onImageClick(int position, View imageView) {

            }
        };
        imageCycleView.setImageResources(imageCycleViewUtils);
    }

    @ReactProp(name = "interval", defaultInt = 3000)
    public void setSpacing(ImageCycleView imageCycleView, int spacing){
        imageCycleView.setSpacing(spacing);
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ImageCycleView view) {
        super.addEventEmitters(reactContext, view);
    }
}
