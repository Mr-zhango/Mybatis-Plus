package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: zhangyang
 * @date: 2019/7/2 10:44
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AAASimpleTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询参数 null 查询全部,返回List
     */
    @Test
    public void selest() {
        List<User> list = userMapper.selectList(null);

        Assert.assertEquals(5, list.size());

        list.forEach(System.out::println);
    }

    /**
     * mp的基本插入方法
     */
    @Test
    public void insertTest(){
        User user = new User();

        user.setName("测试名字");
        user.setAge(18);
        // 这里邮箱为空,mp生成sql语句的时候不会设置这个值,也不会有这个字段
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());

        user.setRemark("巴拉巴拉巴拉");
        int insert = userMapper.insert(user);

        Assert.assertEquals(1,insert);

        System.out.println("影响记录数:"+insert);
    }

}
