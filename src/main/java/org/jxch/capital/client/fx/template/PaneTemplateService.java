package org.jxch.capital.client.fx.template;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaneTemplateService {

    Class<?> templateParamType() default String.class;

    String templateParamValue() default "";

    Class<?> scriptParamType() default String.class;

    String scriptParamValue() default "";

    boolean cache() default true;

    String cacheExpirationCorn() default "0 0 */2 * * ?" ;

}
