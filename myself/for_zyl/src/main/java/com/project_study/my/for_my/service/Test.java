package com.project_study.my.for_my.service;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/15
 * @Version V1.0
 **/
public class Test {

    public static void main(String[] args) {
//        StaticProxySample staticProxySample = new StaticProxySampleImpl();
//        StaticProxy staticProxy = new StaticProxy() ;
//        staticProxy.processBuiness();
           JdkDynamicInterface  jdkDynamic  = JdkDynamicProxy.createProxy();
           jdkDynamic.getData();

    }

}
