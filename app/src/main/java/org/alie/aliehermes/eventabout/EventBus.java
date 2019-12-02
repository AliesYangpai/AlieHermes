package org.alie.aliehermes.eventabout;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alie on 2019/12/1.
 * 类描述
 * 版本
 */
public class EventBus {

    // 需要将注册是类中的订阅方法保存到集合map中
    private Map<Object, List<SubscribeMethod>> cacheMap;

    private ExecutorService executorService;
    private Handler handler;

    private EventBus() {
        cacheMap = new HashMap<>();
        executorService = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    private static class EventBusHolder {
        private static EventBus mInstance = new EventBus();
    }


    public static EventBus getDefault() {
        return EventBusHolder.mInstance;
    }

    public void register(Object object) {
        Class<?> aClass = object.getClass();
        List<SubscribeMethod> subscribeMethods = cacheMap.get(object);
        if (subscribeMethods == null) {
            List<SubscribeMethod> list = getSubscribMethod(aClass);
            cacheMap.put(object, list);
        }
    }


    public void post(final Object student) {
        Set<Object> sets = cacheMap.keySet();
        Iterator<Object> iterator = sets.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            List<SubscribeMethod> list = cacheMap.get(next);
            for (final SubscribeMethod subscribeMethod : list) {
                // 判断传入的参数与之前订阅时，保存的参数是否能对应
                if (subscribeMethod.getEventType().isAssignableFrom(student.getClass())) {
                    ThreadMode threadMode = subscribeMethod.getThreadMode();
                    switch (threadMode) {
                        case Async:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        doInvoke(subscribeMethod, next, student);
                                    }
                                });
                            } else {
                                doInvoke(subscribeMethod, next, student);
                            }
                            break;
                        case MainThread:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                doInvoke(subscribeMethod, next, student);
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        doInvoke(subscribeMethod, next, student);
                                    }
                                });
                            }
                            break;
                        case PostThread:
                            break;
                    }
                }
            }
        }
    }

    /**
     * 通过反射来调用方法
     *
     * @param subscribeMethod 这个是方法
     * @param next            这是 注册者对象
     * @param object          这是 调用者对象
     */
    private void doInvoke(SubscribeMethod subscribeMethod, Object next, Object object) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(next, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * (注册时)寻找订阅的方法
     *
     * @param object
     */
    private List<SubscribeMethod> getSubscribMethod(Object object) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> aClass = (Class<?>) object;
        while (aClass != null) {
            String name = aClass.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") ||
                    name.startsWith("android.")) {
                break;
            }
            Method[] methods = aClass.getDeclaredMethods();
            // 找注解
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                // 判断参数只能是单参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("AlieEventBus only receive one fucking param！！！");
                }

                // 参数判断完成，开始封装到方法中
                ThreadMode threadMode = subscribe.threadMode();
                SubscribeMethod subscribeMethod =
                        new SubscribeMethod(method, threadMode, parameterTypes[0]);
                list.add(subscribeMethod);
            }
            aClass = aClass.getSuperclass();

        }
        return list;
    }
}
