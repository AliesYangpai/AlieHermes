package org.alie.aliehermes.bean;

/**
 * Created by Administrator on 2018/5/21.
 */

public class RequestParameter {
    // 代表每个参数中参数的类型
    private String parameterClassName;

    // 代表参数的值
    private String parameterValue;

    public RequestParameter( ) {
    }

    public RequestParameter(String parameterClassName, String parameterValue) {
        this.parameterClassName = parameterClassName;
        this.parameterValue = parameterValue;
    }

    public String getParameterClassName() {
        return parameterClassName;
    }

    public void setParameterClassName(String parameterClassName) {
        this.parameterClassName = parameterClassName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
