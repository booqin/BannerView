package com.xgbannerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.react.ReactActivity;
//import com.xinguang.reactbanner.ImageCycleView;
//import com.xinguang.reactbanner.ImageCycleViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ReactActivity {


    private String[] strings = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518226&di=b0534ce7158ca8e51d3a199d934bdce1&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e8a157f86d8ca84a0d304fcb9943.jpg%402o.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449646201&di=f87bfff31d6c2500e7a312f465d8edfe&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F287%2F32%2F1E2205O0EVSB_1000x500.jpg" };

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);
//        ImageCycleView imageCycleView = (ImageCycleView) findViewById(R.id.banner);
//        imageCycleView.setBackgroundColor(ContextCompat.getColor(this, R.color.pl_theme_color));
//        ImageCycleViewUtils<String> imageCycleViewUtils = new ImageCycleViewUtils<String>() {
//            @Override
//            public List<String> getList() {
//                return Arrays.asList(strings);
//            }
//
//            @Override
//            public void displayImage(String url, ImageView imageView) {
//                Glide.with(MainActivity.this).load(url).into(imageView);
//            }
//
//            @Override
//            public void onImageClick(int position, View imageView) {
//
//            }
//        };
//        imageCycleView.setImageResources(imageCycleViewUtils);
//    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "XGBannerDemo";
    }
}
