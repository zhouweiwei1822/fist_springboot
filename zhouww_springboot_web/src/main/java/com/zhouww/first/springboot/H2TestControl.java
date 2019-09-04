package com.zhouww.first.springboot;

import com.alibaba.fastjson.JSONObject;
import com.zhouww.first.modules.entity.User;
import com.zhouww.first.springboot.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class H2TestControl {
@Autowired
private UserInfo userInfo;
    @RequestMapping(value = "/trr",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String test(){
        User user=userInfo.getUserById(1);
        System.out.println(JSONObject.toJSONString(user)+"=================================");
        return JSONObject.toJSONString(user);
    }
}
