package com.zhouww.first.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TestService {
    @RequestMapping("/test")
    public String  testGet(HttpServletRequest req){

        return  "ffff"+req.getParameter("qq");
    }

}
