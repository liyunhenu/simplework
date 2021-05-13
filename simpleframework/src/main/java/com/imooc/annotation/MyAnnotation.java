package com.imooc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Documented
public @interface MyAnnotation {

    String value() default "1";
}
