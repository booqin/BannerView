package com.xinguang.reactbanner;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.xinguang.reactbanner.react.ReactBannerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vitozhang on 2018/6/8.
 */

public class ReactBannerPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> list = new ArrayList<>();
        list.add(new ReactBannerManager());
        return list;
    }
}
