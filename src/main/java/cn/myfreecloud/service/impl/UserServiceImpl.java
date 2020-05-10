package cn.myfreecloud.service.impl;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import cn.myfreecloud.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
