package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2019/7/2 11:35
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据id进行删除
     */
    @Test
    public void deleteByIds() {
        int i = userMapper.deleteById(1184693036882415618L);
        System.out.println(i);
    }

    @Test
    public void deleteByMap() {
        Map<String,Object> columnMap = new HashMap<>();

        columnMap.put("name","刘黑雨");
        columnMap.put("age",21345);

        int i = userMapper.deleteByMap(columnMap);
        System.out.println(i);
    }

    @Test
    public void deleteBatchIds() {
        int i = userMapper.deleteBatchIds(Arrays.asList(1259453021021073409L,1259453157449154562L));
        System.out.println(i);
    }


    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();

        lambdaQueryWrapper.eq(User::getAge,18).or().gt(User::getAge,41);

        int delete = userMapper.delete(lambdaQueryWrapper);

        System.out.println(delete);
    }




}
