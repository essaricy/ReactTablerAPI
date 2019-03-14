package com.softvision.digital.web.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
public class SwaggerDoc {

    @Bean
    public Docket getApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.softvision")).paths(PathSelectors.any()).build()
                .pathMapping("/")
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(getResponseMessage(HttpStatus.UNAUTHORIZED),
                                getResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)))
                //.securitySchemes(newArrayList(apiKey())).securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(true);
    }

    private ResponseMessage getResponseMessage(HttpStatus httpStatus) {
        return new ResponseMessageBuilder().code(httpStatus.value()).message(httpStatus.getReasonPhrase())
                .responseModel(new ModelRef("ResultDto")).build();
    }

////    private ApiKey apiKey() {
////        return new ApiKey(AuthConstant.AUTH_TOKEN, AuthConstant.HTTP_X_HEADER_AUTH_TOKEN, "header");
////    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return newArrayList(new SecurityReference(AuthConstant.AUTH_TOKEN, authorizationScopes));
//    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1).defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE).filter(false).maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA).showExtensions(false).tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build();
    }

}