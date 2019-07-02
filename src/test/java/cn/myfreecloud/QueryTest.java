package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

/**
 * @author: zhangyang
 * @date: 2019/7/2 11:35
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void queryById(){
        User user = userMapper.selectById(1094590409767661570L);
        Assert.assertEquals(String.valueOf(1094590409767661570L),user.getId().toString());
        System.out.println(user);
    }


    @Test
    public void queryByIds(){

        List<Long> idsList = Arrays.asList(1088250446457389058L, 1094592041087729666L, 1145890554823868418L);

        List<User> users = userMapper.selectBatchIds(idsList);

        users.forEach(System.out::println);
    }

    @Test
    public void queryByMap(){

        HashMap<String,Object> map = new HashMap();

        //条件查询 map中的键就是数据库中的列(重点)
        map.put("name","张强");
        map.put("age","19");


        List<User> users = userMapper.selectByMap(map);

        users.forEach(System.out::println);

    }


    /**
     * 1.姓名中包含雨并且年龄小于40的人
     */
    @Test
    public void queryByWrapper(){

        HashMap<String,Object> map = new HashMap();

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //QueryWrapper<User> query = Wrappers.<User>query();
        QueryWrapper<User> userQueryWrapper = queryWrapper.like("name", "雨").lt("age", 40);

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 2.姓名中包含雨并且年龄>=20并且<=40的并且email不为空的
     */
    @Test
    public void queryByWrapper2(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper.like("name", "雨").between("age",20,40).isNotNull("email");

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 3.姓名为王姓或者年龄大于等于25,按照年龄降序排列,年龄相同的按照id升序排列
     * name like '王%' or age >=25 order by age desc,id asc
     *
     */
    @Test
    public void queryByWrapper3(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper
                .likeRight("name", "王")
                .or()
                .ge("age",25)
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 4.创建日期为2019年2月14日 并且直属上级 名字为王姓的
     * "date_format(create_time,'%Y-%m-%d') and manager_id in (select id from user where name like '王%')"
     *
     */
    @Test
    public void queryByWrapper4(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

       queryWrapper
                .apply("date_format(create_time,'%Y-%m-%d') = {0}","2019-02-14")
                .inSql("manager_id","select id from user where name like '王%' ");

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 5.名字为王姓(年龄小于40或者邮箱部位空)
     * "name like '王%' and (age < 40 or email is not null)"
     *
     */
    @Test
    public void queryByWrapper5(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .likeRight("name", "王").and(qw -> qw.lt("age",40).or().isNotNull("email"));

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 6.名字为王姓 或者 (年龄小于40并且年龄大于20并且邮箱不为空)
     * "name like '王%' or (age < 40 and age >20 and email is not null)"
     *
     */
    @Test
    public void queryByWrapper6(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .likeRight("name", "王")
                .or(
                        qw -> qw.lt("age",40)
                        .gt("age",20)
                        .isNotNull("email")
                    );

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 7.(年龄小于40 或者 邮箱不为空) 并且名字为王姓的
     * "(age < 40 and email is not null) and name like '王%' or "
     * 前括号 nested
     */
    @Test
    public void queryByWrapper7(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
               .nested(
                       qw -> qw.lt("age",40)
                       .or().isNotNull("email")
                      )
                .likeRight("name","王");

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 8.年龄为30.31.34.35
     * "age in (30,31,34,35)"
     * .in("age", Arrays.asList(30,31,34,35));
     */
    @Test
    public void queryByWrapper8(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .in("age", Arrays.asList(30,31,34,35));

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 9.返回满足条件的一个数据
     * "limit 1"
     * 加到最后 last函数 .last("limit 1");
     */
    @Test
    public void queryByWrapper9(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .in("age", Arrays.asList(30,31,34,35))
                .last("limit 1");

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 10.名字中包含雨并且年龄小于40
     * "name like '%雨%' and age < 40"
     * 只是查询部分字段 .select("id","name")
     */
    @Test
    public void queryByWrapper10(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .select("id","name").like("name","雨").le("age",40);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 11.名字中包含雨并且年龄小于40
     * "name like '%雨%' and age < 40"
     * 部分排除
     * 加到最后 last函数
     */
    @Test
    public void queryByWrapper11(){

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
               .like("name","雨").le("age",40)
                .select(User.class,info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id") );

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    @Test
    public void queryByWrapper12Tes(){
        queryByWrapper12("强","");
    }

    /**
     * 12.条件查询
     * "name like '%雨%' and age < 40"
     * 部分排除
     * 加到最后 last函数
     */
    public void queryByWrapper12(String name,String email){


        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .like(!StringUtils.isEmpty(name),"name",name)
                .like(!StringUtils.isEmpty(email),"email",email);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 12.
     * 部分排除
     * 加到最后 last函数
     */
    @Test
    public void queryByWrapper12(){

        //条件查询 map中的键就是数据库中的列(重点)


        User whereUser = new User();
        whereUser.setName("刘红雨");
        whereUser.setAge(32);


        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);

        //两个查询不影响   注意条件重复问题
        queryWrapper
                .like("name","雨").le("age",40);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


}
