package demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PersonInfoAnnotation {
    //姓名
    public String name();

    //年龄
    public int age() default 19;

    //性别
    public String gender() default "男";

    //语言
    public String[] language();

}
