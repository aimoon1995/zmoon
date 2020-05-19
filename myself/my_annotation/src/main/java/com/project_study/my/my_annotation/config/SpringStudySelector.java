package com.project_study.my.my_annotation.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName SpringStudySelector
 * @Description: TODO
 * @Author zyl
 * @Date 2020/4/26
 * @Version V1.0
 **/
public class SpringStudySelector implements ImportSelector, BeanFactoryAware {
    private  BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;

    }

//    @Override
//    public String[] selectImports(AnnotationMetadata annotationMetadata) {
//        annotationMetadata.getAnnotationTypes().forEach(System.out::println);
//        System.out.println(beanFactory);
//        return new String[]{AppConfig.class.getName()};
//    }

    public static void main(String args[]) {
        try {
            String s="ABCDE";
            byte b[]=s.getBytes();
            FileOutputStream file=new FileOutputStream("test.txt", true);
            file.write(b);
            file.close();
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
    }
}
