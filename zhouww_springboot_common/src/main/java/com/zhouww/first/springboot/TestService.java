package com.zhouww.first.springboot;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class TestService {
    @RequestMapping("/test")
    public String  testGet(HttpServletRequest req){

        return  "ffff"+req.getParameter("qq");
    }
    @RequestMapping("/test1")
    public String  testGet(HttpServletRequest req, @RequestBody MultipartFile  file) throws IOException {
        FileOutputStream fil=new FileOutputStream("D:\\POReceiveReport1.pdf");
        fil.write(file.getBytes());
        return  "ffff"+req.getParameter("qq");
    }
}
