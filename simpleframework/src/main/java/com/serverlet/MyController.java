package com.serverlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @ResponseBody
    @RequestMapping(value="/hello",produces = "text/html;charset=utf-8")
    public String sayHello(){
        return "Hello,new Job!";
    }


}
