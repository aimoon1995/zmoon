package com.project_study.my.for_my.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName JdkDynamicProxy
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/16
 * @Version V1.0
 **/
public class JdkDynamicProxy implements InvocationHandler {

    private  JdkDynamicImpl jdkDynamic = null;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (jdkDynamic == null) {
            jdkDynamic = new JdkDynamicImpl();
        }
        System.out.println("大风");
        Object result =  method.invoke(jdkDynamic,args);
        return result;
    }

    static JdkDynamicInterface createProxy() {
        JdkDynamicInterface proxy = (JdkDynamicInterface) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), //当前类的类加载器
                new Class[]{JdkDynamicInterface.class}, //被代理的主题接口
                new JdkDynamicProxy() // 代理对象，这里是当前的对象
        );
        return proxy;
    }
}
