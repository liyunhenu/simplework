package demo.annotation;

@CourseInfoAnnotation(courseName = "剑指面试",courseTag = "面试",courseProfile = "这是课程简介")
public class ImoocCourse {
@PersonInfoAnnotation(name = "翔仔",language = {"java","pathon"})
    public String author;

@CourseInfoAnnotation(courseName = "方法级别课程",courseTag = "面试-方法级别",courseProfile = "这是课程简介-方法级别")
    public void getCourseInfo(){

    }
}
