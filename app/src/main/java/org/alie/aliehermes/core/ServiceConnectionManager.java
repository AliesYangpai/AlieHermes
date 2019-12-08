package org.alie.aliehermes.core;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import org.alie.aliehermes.EventBusService;
import org.alie.aliehermes.Request;
import org.alie.aliehermes.Response;
import org.alie.aliehermes.service.HermesService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alie on 2019/12/8.
 * 类描述
 * 版本
 */
public class ServiceConnectionManager {


    //Class对应的链接对象
    private final ConcurrentHashMap<Class<? extends HermesService>, HermesServiceConnection> mHermesServiceConnections = new ConcurrentHashMap<Class<? extends HermesService>, HermesServiceConnection>();
    //    Class  对应的Binder  对象
    private final ConcurrentHashMap<Class<? extends HermesService>, EventBusService> mHermesServices = new ConcurrentHashMap<Class<? extends HermesService>, EventBusService>();


    private ServiceConnectionManager() {
    }

    public Response request(Class<HermesService> hermesServiceClass, Request request) {
        return null;
    }

    private static class ServiceConnectionManagerHolder {
        private static ServiceConnectionManager manager = new ServiceConnectionManager();
    }

    public static ServiceConnectionManager getInstance() {
        return ServiceConnectionManagerHolder.manager;
    }

    public void bind(Context context, String packageName, Class<? extends HermesService> service) {
        HermesServiceConnection connection = new HermesServiceConnection(service);
        mHermesServiceConnections.put(service, connection);
        Intent intent ;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    //     接受远端的binder 对象   进程B就可以了通过Binder对象去操作 服务端的 方法
    private class HermesServiceConnection implements ServiceConnection {
        private Class<? extends HermesService> mClass;

        HermesServiceConnection(Class<? extends HermesService> service) {
            mClass = service;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            EventBusService hermesService = EventBusService.Stub.asInterface(service);
            mHermesServices.put(mClass, hermesService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mHermesServices.remove(mClass);
        }
    }

}
