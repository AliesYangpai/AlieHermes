package org.alie.aliehermes.eventabout;

import java.lang.reflect.Method;

/**
 * Created by Alie on 2019/12/1.
 * 注册的方法
 * 版本
 */
public class SubscribeMethod {
    private Method method;
    private ThreadMode threadMode;
    private Class<?> eventType;

    public SubscribeMethod() {
    }

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public void setEventType(Class<?> eventType) {
        this.eventType = eventType;
    }
}
