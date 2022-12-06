package org.sid.msModules.swagger;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("org.sid.msModules.web"))
                .paths(paths()).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger REST API")
                .description("Swagger Description details")
                .version("1.0")
                .contact("EL MOUSAID Ismail")
                .termsOfServiceUrl("http://intellitech.pro/")
                .build();
    }

    @SuppressWarnings("unchecked")
    private Predicate paths() {
        return or(
                regex("/branches.*"),
                regex("/deploiements.*"),
                regex("/environnements.*"),
                regex("/modules.*"),
                regex("/tags.*"));
    }

}
