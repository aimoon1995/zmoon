package com.project_study.my.for_my.service;

/**
 * @ClassName StaticProxy
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/15
 * @Version V1.0
 **/
public class StaticProxy  implements   StaticProxySample{
      private  StaticProxySample staticProxySample = null;

    @Override
    public void processBuiness() {
        if (staticProxySample == null) {
            staticProxySample = new StaticProxySampleImpl();
        }
        System.out.println("我去拿我的40米大刀");
        staticProxySample.processBuiness();
        System.out.println("我40米大刀砍断了");

    }
}
