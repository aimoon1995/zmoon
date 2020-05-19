package com.project_study.my.my_annotation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName CustomValidate
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/27
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValidate {
    //分组
    Class<?>[] groups() default {};

    //定义是否一次性显示全部错误消息，默认为false(不显示全部信息)
    boolean showAll() default false;

}
