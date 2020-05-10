package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EARTest {

    @Test
    public void insertTest() {
        User user = new User();

        user.setName("测试1");
        user.setAge(18);
        // 这里邮箱为空,mp生成sql语句的时候不会设置这个值,也不会有这个字段
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setRemark("巴拉巴拉巴拉");


        boolean insert = user.insert();
        System.out.println(insert);
    }

    @Test
    public void selectById() {
        User user = new User();
        User userSelect = user.selectById(1087982257332887553L);
        System.out.println(user == userSelect);
        System.out.println(userSelect);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1087982257332887553L);

        User userSelect = user.selectById();
        System.out.println(user == userSelect);
        System.out.println(userSelect);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1259455613331632129L);
        user.setName("更新测试");

        boolean b = user.updateById();
        System.out.println(b);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(1259455613331632129L);
        // 只是根据id进行删除,和其他属性没关系
        user.setName("1234");

        boolean b = user.deleteById();
        System.out.println(b);
    }

    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setName("张强");
        // 只是根据id进行删除,和其他属性没关系
        user.setAge(24);
        user.setEmail("zq@baomidou.com");
        user.setManagerId(1087982257332887553L);
        user.setCreateTime(LocalDateTime.now());

        boolean b = user.insertOrUpdate();
        System.out.println(b);
    }
}
