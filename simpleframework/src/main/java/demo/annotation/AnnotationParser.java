package demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {

    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class immocCourseClass  = Class.forName("demo.annotation.ImoocCourse");
        //Annotation[] annotations=immocCourseClass.getAnnotations();
        if(immocCourseClass.isAnnotationPresent(CourseInfoAnnotation.class)){
            CourseInfoAnnotation courseInfoAnnotation= (CourseInfoAnnotation) immocCourseClass.getAnnotation(CourseInfoAnnotation.class);
            System.out.println("课程名字:"+courseInfoAnnotation.courseName()+"\n"+"课程标签:"+courseInfoAnnotation.courseTag()+"\n"+"课程序号:"+courseInfoAnnotation.courseIndex()
                    +"\n"+"课程简介:"+courseInfoAnnotation.courseProfile());
        }
        /*for (Annotation annotation:annotations) {
            CourseInfoAnnotation courseInfoAnnotation= (CourseInfoAnnotation) annotation;
            System.out.println("课程名字:"+courseInfoAnnotation.courseName()+"\n"+"课程标签:"+courseInfoAnnotation.courseTag()+"\n"+"课程序号:"+courseInfoAnnotation.courseIndex()
            +"\n"+"课程简介:"+courseInfoAnnotation.courseProfile());
        }*/

    }

    public static void parseFieldAnnotation() throws ClassNotFoundException {
        Class immocCourseClass  = Class.forName("demo.annotation.ImoocCourse");
        Field[] fields=immocCourseClass.getDeclaredFields();
        for (Field field:fields
             ) {
            if(field.isAnnotationPresent(PersonInfoAnnotation.class)){
                PersonInfoAnnotation annotation=field.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("名字:"+annotation.name()+"\n"+"年龄:"+annotation.age()+"\n"+"性别:"+annotation.gender());
                String[] languages=annotation.language();
                for (String language:languages
                     ) {
                    System.out.println("擅长语言名字:"+language);
                }
            }
        }
    }

    public static void parseMethodAnnotation() throws ClassNotFoundException {
        Class immocCourseClass  = Class.forName("demo.annotation.ImoocCourse");
        Method[] methods=immocCourseClass.getDeclaredMethods();
        for (Method method:methods) {
            if(method.isAnnotationPresent(CourseInfoAnnotation.class)){
                CourseInfoAnnotation courseInfoAnnotation=method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println("课程名字:"+courseInfoAnnotation.courseName()+"\n"+"课程标签:"+courseInfoAnnotation.courseTag()+"\n"+"课程序号:"+courseInfoAnnotation.courseIndex()
                        +"\n"+"课程简介:"+courseInfoAnnotation.courseProfile());

            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        //parseTypeAnnotation();
        parseFieldAnnotation();
        //parseMethodAnnotation();
    }
}
