package com.xinguang.reactbanner.react;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.xinguang.reactbanner.R;
import com.xinguang.reactbanner.view.Constants;
import com.xinguang.reactbanner.view.ImageCycleView;
import com.xinguang.reactbanner.view.ImageCycleViewUtils;

import junit.framework.Assert;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by vitozhang on 2018/6/8.
 */

public class ReactBannerManager extends ViewGroupManager<ImageCycleView> {

    private static final String NAME = "BannerView";
    private EventDispatcher mEventDispatcher;
    private RequestOptions mOptions;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ImageCycleView createViewInstance(ThemedReactContext reactContext) {
        ImageCycleView imageCycleView = new ImageCycleView(reactContext);
        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mOptions = new RequestOptions()
                .error(R.drawable.pl_placeholder)
                .placeholder(R.drawable.pl_placeholder);
        return imageCycleView;
    }

    @Override
    public boolean hasConstants() {
        return true;
    }

    @ReactProp(name = "dataSet")
    public void setDataSet(final ImageCycleView imageCycleView, final ReadableArray array){
        ImageCycleViewUtils imageCycleViewUtils = new ImageCycleViewUtils() {
            @Override
            public List getList() {
                return array.toArrayList();
            }

            @Override
            public void displayImage(Object object, ImageView imageView) {
                if (object instanceof String) {
                    if (!TextUtils.isEmpty((String)object)) {

                        Glide.with(imageCycleView)
                                .load((String)object)
                                .thumbnail(0.1f)
                                .apply(mOptions)
                                .into(imageView);
                    }
                }
            }

            @Override
            public void onImageClick(int position, View imageView) {
                mEventDispatcher.dispatchEvent(new ItemClickEvent(imageCycleView.getId(), position));
            }
        };
        imageCycleView.setImageResources(imageCycleViewUtils);
    }

    @ReactProp(name = "interval", defaultInt = 3000)
    public void setSpacing(ImageCycleView imageCycleView, int spacing){
        imageCycleView.setSpacing(spacing);
    }

    @ReactProp(name = "playEnable", defaultBoolean = true)
    public void setPlayEnable(ImageCycleView imageCycleView, boolean enable){
        imageCycleView.setIsManualLoop(enable);
    }

    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "topPress", MapBuilder.of("registrationName", "onPress"));
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("startLoop", Constants.CommandType.COMMAND_START_LOOP, "stopLoop", Constants.CommandType.COMMAND_STOP_LOOP);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void receiveCommand(ImageCycleView root, int commandId, @Nullable ReadableArray args) {
        Assert.assertNotNull(root);

        switch (commandId){
            case Constants.CommandType.COMMAND_START_LOOP:
                root.startImageTimerTask();
                break;
            case Constants.CommandType.COMMAND_STOP_LOOP:
                root.stopImageTimerTask();
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandId,
                        getClass().getSimpleName()));
        }

    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ImageCycleView view) {
        mEventDispatcher.dispatchEvent(new ItemClickEvent(view.getId(), 0));
    }
}
