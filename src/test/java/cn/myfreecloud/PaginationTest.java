package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2019/7/2 11:35
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaginationTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 分页查询测试方法
     */
    @Test
    public void selectObjectPage() {
        // 第一种构造方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.ge("age",26);


        // false 表示不查询总记录数
        // 当前第一页,每页查询2个数据
        Page<User> userPage = new Page<>(0, 2,false);

        IPage<User> userIPage = userMapper.selectPage(userPage, queryWrapper);


        System.out.println("总页数:"+userIPage.getPages());
        System.out.println("总记录数:"+userIPage.getTotal());
        System.out.println("查询结果:");

        List<User> records = userIPage.getRecords();
        records.forEach(System.out::println);
    }

    /**
     * 分页查询测试方法
     */
    @Test
    public void selectMapPage() {
        // 第一种构造方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.ge("age",26);


        // 当前第一页,每页查询2个数据
        Page<User> userPage = new Page<>(0, 2);

        IPage<Map<String,Object>> userIPage = userMapper.selectMapsPage(userPage, queryWrapper);


        System.out.println("总页数:"+userIPage.getPages());
        System.out.println("总记录数:"+userIPage.getTotal());
        System.out.println("查询结果:");

        List<Map<String, Object>> records = userIPage.getRecords();
        records.forEach(System.out::println);
    }

    /**
     * 分页查询测试方法
     */
    @Test
    public void selectMyPage() {
        // 第一种构造方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.ge("age",26);


        // 当前第一页,每页查询2个数据
        Page<User> userPage = new Page<>(0, 2);

        IPage<User> userIPage = userMapper.selectUserPage(userPage, queryWrapper);


        System.out.println("总页数:"+userIPage.getPages());
        System.out.println("总记录数:"+userIPage.getTotal());
        System.out.println("查询结果:");

        List<User> records = userIPage.getRecords();
        records.forEach(System.out::println);
    }
}
