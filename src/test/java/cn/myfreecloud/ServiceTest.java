package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
        System.out.println(one);
    }

    @Test
    public void chain() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();

        userList.forEach(System.out::println);
    }

    @Test
    public void updateChain() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 26).set(User::getAge, 100).update();

        System.out.println(update);
    }

    @Test
    public void deleteChain() {
        boolean remove = userService.lambdaUpdate().eq(User::getAge, 111).remove();

        System.out.println(remove);
    }
}
