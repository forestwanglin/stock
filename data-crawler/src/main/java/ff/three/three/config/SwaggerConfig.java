package ff.three.three.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Forest Wang
 * @package io.merculet.config
 * @class SwaggerConfig
 * @email forest@magicwindow.cn
 * @date 2018/7/4 21:56
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("cn.seeotc.service.app.root.controller"))
                //.apis(RequestHandlerSelectors.basePackage("cn.seeotc.service.app.account.controller"))
                .apis(RequestHandlerSelectors.basePackage("io.merculet"))
                //.apis(SoRequestHandlerSelectors.allpro())
                //.apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectorsExt.withInterface())
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/api/*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("UAT/ETH transaction")
                .description("transfer UAT and ETH")
                .termsOfServiceUrl("")
                .contact(new Contact("name", "url", "email"))
                .version("1.0.0")
                .build();
    }


}
