package com.xinguang.reactbanner.view;

/**
 * 常量
 * Created by vitozhang on 2018/6/13.
 */

public class Constants {
    /**
     * 命令类型，用于映射JS调对Native端的调用
     */
    public interface CommandType{
        /** 开始循环 **/
        int COMMAND_START_LOOP = 1;
        /** 停止循环 **/
        int COMMAND_STOP_LOOP = 2;
    }
}
