package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2019/7/2 11:35
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据id进行更新的方法
     */
    @Test
    public void updateByIds() {

        User user = new User();
        user.setId(1088248166370832385L);

        user.setAge(26);
        user.setEmail("wtf2@baomidou.com");
        int i = userMapper.updateById(user);

        System.out.println(i);

    }


    /**
     * 根据条件进行查询,然后进行更新的方法
     */
    @Test
    public void updateByWrapper() {


        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",28);

        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        user.setAge(29);
        int update = userMapper.update(user, updateWrapper);
        System.out.println(update);


    }

    /**
     * 根据条件进行查询,然后更新少量字段的快速方法
     */
    @Test
    public void updateByWrapperQuick() {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",29)
                .set("age",21);

        int update = userMapper.update(null, updateWrapper);
        System.out.println(update);


    }


    /**
     * 根据条件进行查询,然后更新少量字段的快速方法
     */
    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(User::getName,"李艺伟").eq(User::getAge,21).set(User::getAge,30);

        int update = userMapper.update(null, lambdaUpdateWrapper);
        System.out.println(update);
    }


    /**
     * 根据条件进行查询,然后更新少量字段的快速方法
     */
    @Test
    public void updateByWrapperLambdaChain() {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getName, "李艺伟").eq(User::getAge,30).set(User::getAge, 31).update();
        System.out.println(update);
    }
}
