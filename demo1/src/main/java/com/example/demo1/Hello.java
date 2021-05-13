package com.example.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Hello {
    private Logger logger= LoggerFactory.getLogger(Hello.class);

    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public Map<String,Object> hello(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        String name="liyun";
        String score="100";
        result.put("name",name);
        result.put("score",score);
        logger.info(result.toString());

        return result;

    }

}
