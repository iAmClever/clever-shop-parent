package com.clever.aop;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by clever on 2019/9/10.
 */
public class GetLog extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("level", eventObject.getLevel().levelStr);
        jsonObject.put("class", eventObject.getLoggerName());
        //jsonObject.put("timestamp", new Date(eventObject.getTimeStamp()).toString("yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("message", eventObject.getFormattedMessage());

        System.out.println(">>>>>>>>>>>>>>>"+jsonObject.toJSONString());
    }
}
