package org.alie.aliehermes.core;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import org.alie.aliehermes.Request;
import org.alie.aliehermes.Response;
import org.alie.aliehermes.UserManager;
import org.alie.aliehermes.annotion.ClassId;
import org.alie.aliehermes.bean.RequestBean;
import org.alie.aliehermes.bean.RequestParameter;
import org.alie.aliehermes.service.HermesService;
import org.alie.aliehermes.util.TypeUtils;

import java.lang.reflect.Method;

/**
 * Created by Alie on 2019/12/7.
 * 类描述
 * 版本
 */
public class Hermes {
    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_GET = 1;
    private static Hermes mInstance;
    private Context sContext;
    private ServiceConnectionManager serviceConnectionManager;
    private TypeCenter typeCenter;

    private Hermes() {
        serviceConnectionManager = ServiceConnectionManager.getInstance();
        typeCenter = TypeCenter.getInstance();
    }

    public static Hermes getDefault() {
        if (mInstance == null) {
            synchronized (Hermes.class) {
                if (mInstance == null) {
                    mInstance = new Hermes();
                }
            }
        }
        return mInstance;
    }


    //================A进程中调用

    /**
     * hermes中的注册方法，来进行注册的
     *
     * @param clazz
     */
    public void register(Class<?> clazz) {
        typeCenter.registerClass(clazz);
    }


    // ================B进程中调用
    public void connect(Context context, Class<? extends HermesService> service) {
        connectApp(context, null, service);
    }

    private void connectApp(Context context, String packageName, Class<? extends HermesService> service) {
        init(context);
        serviceConnectionManager.bind(context.getApplicationContext(), packageName, service);
    }

    public void init(Context context) {
        sContext = context.getApplicationContext();
    }

    /**
     * 获取进程a的单例,
     * 主要是为了防止方法重载
     *
     * @return
     */
    public <T> T getInstance(Class<T> clazz, Object... parameters) {
//Class<T> clazz, Object... parameters   ====》  Request    ----->Responce
        Response response = sendRequest(HermesService.class, clazz, null, parameters);
        return null;
    }

    private <T> Response sendRequest(Class<HermesService> hermesServiceClass,
                                     Class<T> clazz,
                                     Method method,
                                     Object[] parameters) {
        RequestBean requestBean = new RequestBean();
        String className = null;
        if (clazz.getAnnotation(ClassId.class) == null) {
//            当
            requestBean.setClassName(clazz.getName());
            requestBean.setResultClassName(clazz.getName());
        } else {
//            返回类型的全类名
            requestBean.setClassName(clazz.getAnnotation(ClassId.class).value());
            requestBean.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if (method != null) {
//            方法名 统一   传   方法名+参数名  getInstance(java.lang.String)
            requestBean.setMethodName(TypeUtils.getMethodId(method));

        }
//
        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = JSON.toJSONString(parameter);

                RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                requestParameters[i] = requestParameter;
            }
        }

        if (requestParameters != null) {
            requestBean.setRequestParameter(requestParameters);
        }

//        请求获取单例 ----》对象 ----------》调用对象的方法
        Request request = new Request(JSON.toJSONString(requestBean), TYPE_GET);
        return serviceConnectionManager.request(hermesServiceClass, request);
    }


}
