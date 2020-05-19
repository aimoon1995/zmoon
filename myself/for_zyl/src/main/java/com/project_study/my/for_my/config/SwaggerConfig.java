package com.project_study.my.for_my.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("access_token").description("令牌").modelRef(new ModelRef("string")).parameterType("query").required(false).build();
        pars.add(tokenPar.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project_study"))
                .paths(PathSelectors.any())
                .build()
               .globalOperationParameters(pars);






    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("studty-projectAPI接口文档")
                .description("studty-project接口管理平台，http://localhost:8085/swagger-ui.html")
                .termsOfServiceUrl("localhost:8085/swagger-ui.html")
                .version("2.0")
                .build();
    }

}
