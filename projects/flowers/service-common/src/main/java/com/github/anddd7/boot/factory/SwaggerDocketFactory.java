package com.github.anddd7.boot.factory;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.experimental.UtilityClass;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@UtilityClass
public class SwaggerDocketFactory {

  public static Docket createDocket(String groupName, ApiInfo apiInfo,
      String protocol, Class... ignoreParameters) {
    TypeResolver typeResolver = new TypeResolver();
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName(groupName)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo)
        .useDefaultResponseMessages(false)
        .ignoredParameterTypes(addPrincipalToIgnoredParameters(ignoreParameters))
        .directModelSubstitute(Collection.class, List.class)
        .alternateTypeRules(createAlternateTypeRule(typeResolver))
//        .globalOperationParameters(buildGlobalParams())
        .protocols(newHashSet(protocol));
  }

  private static AlternateTypeRule createAlternateTypeRule(TypeResolver typeResolver) {
    return newRule(
        typeResolver.resolve(Collection.class, WildcardType.class),
        typeResolver.resolve(List.class, WildcardType.class)
    );
  }

  private static Class[] addPrincipalToIgnoredParameters(Class... ignoreParameters) {
    List<Class> ignoreParametersList = new ArrayList<>();
    ignoreParametersList.add(Principal.class);
    ignoreParametersList.addAll(asList(ignoreParameters));
    return ignoreParametersList.toArray(new Class[0]);
  }
}
