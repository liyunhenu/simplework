package com.imooc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name","简易Spring框架");
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req,resp);
        //super.doGet(req, resp);
        String name=new String("nihao");
        File file=new File("/Users/liyun");
        File[] files=file.listFiles();


    }


}
