package com.zhouww.first.springboot.service.ServiceImpl;

import com.zhouww.first.modules.dao.UserMapper;
import com.zhouww.first.modules.entity.User;
import com.zhouww.first.springboot.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl implements UserInfo {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
