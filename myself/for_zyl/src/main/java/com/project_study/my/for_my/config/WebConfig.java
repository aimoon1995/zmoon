package com.project_study.my.for_my.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//    @Autowired
//    private MonitorVersionInterceptors monitorVersionInterceptors;

    @Value("${file.pathPatterns}")
    private String pathPatterns;

    @Value("${file.downloadWin}")
    private String downloadWin;





//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 自定义拦截器，添加拦截路径和排除拦截路径
//        //    registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/**");
//        InterceptorRegistration addInterceptor = registry.addInterceptor(loginInterceptor).order(2);
//        InterceptorRegistration addMonitorVersionInterceptor = registry.addInterceptor(monitorVersionInterceptors).order(1);
//        // 排除配置
//        addInterceptor.excludePathPatterns("/index/**", "/error", "/home/**");
//        addInterceptor.excludePathPatterns("/index/**", "/home/**", "/error");
//        addInterceptor.excludePathPatterns("/**/login/**");
//        addInterceptor.excludePathPatterns("/**/version/monitor_upgrade");
//        addInterceptor.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html", "/swagger-resources/**");
//
//        addMonitorVersionInterceptor.excludePathPatterns("/error");
//        addMonitorVersionInterceptor.excludePathPatterns("/**/version/monitor_upgrade");
//        addMonitorVersionInterceptor.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html", "/swagger-resources/**");
//        // 拦截配置
//        addInterceptor.addPathPatterns("/**");
//        addMonitorVersionInterceptor.addPathPatterns("/**");
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**").exposedHeaders("USER_TOKEN");
//    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
//        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
//            registry.addResourceHandler("/"+pathPatterns+"/**").
//                    addResourceLocations("file:" + downloadWin);
//        }else {//linux和mac系统
            registry.addResourceHandler("/"+pathPatterns+"/**").
                   // addResourceLocations("file:" + downloadLinux);
                    addResourceLocations("file:" + downloadWin);

    //    }
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }



}
