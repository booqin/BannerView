package com.xinguang.reactbanner.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * 点击事件
 * Native端发送到JS端的事件
 * Created by BoQin on 2018/6/13.
 */

public class ItemClickEvent extends Event<ItemClickEvent>{
    /** 映射事件名 **/
    private static final String TOP_PRESS = "topPress";

    private int mId;
    /** 被点击的序号 **/
    private int mIndex;

    /**
     *
     * @param id 对应的ViewId，在JS端作为身份标识
     * @param index 点击的item序号
     */
    public ItemClickEvent(int id, int index){
        super(id);
        mId = id;
        mIndex = index;
    }

    @Override
    public String getEventName() {
        return TOP_PRESS;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(mId, getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("index", mIndex);
        return eventData;
    }
}
