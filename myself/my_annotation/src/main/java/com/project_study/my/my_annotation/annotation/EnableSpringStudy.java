package com.project_study.my.my_annotation.annotation;

import com.project_study.my.my_annotation.config.SpringStudySelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName EnableSpringStudy
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/26
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface EnableSpringStudy {

}
