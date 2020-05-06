package com.farinfo.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @ConditionalOnProperty:
 * 读取配置文件中前缀为swagger2的配置，属性名为enable，值为true。
 * 当条件成立，此配置类被激活。
 */
@Configuration
@EnableSwagger2
//@ConditionalOnProperty(prefix = "swagger2",value = {"enable"},havingValue = "true")
public class SwaggerConfig {
    // 定义分隔符
    private static final String splitor = ";";
    @Bean
    public Docket createRestApi() {
        //添加head参数start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("Authentication").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //多配制扫描
                .apis(basePackage("com.farinfo.controller;"))
                .paths(PathSelectors.any())
                .build()
                /*.globalOperationParameters(pars)*/;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台对接使用Swagger2构建RESTful APIs")
                //.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                //.termsOfServiceUrl("http://blog.didispace.com/")
                .contact("yuanzhijun on 2019/06/13")
                .version("1.0")
                .build();
    }


    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {

        return Optional.fromNullable(input.getHandlerMethod().getMethod().getDeclaringClass());
    }
}
