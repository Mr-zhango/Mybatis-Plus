package cn.myfreecloud;

import cn.myfreecloud.entity.User;
import cn.myfreecloud.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class BBBQueryTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 普通查询:通过id查询
     */
    @Test
    public void queryById() {
        User user = userMapper.selectById(1094590409767661570L);
        Assert.assertEquals(String.valueOf(1094590409767661570L), user.getId().toString());
        System.out.println(user);
    }


    /**
     * 通过idList进行查询,返回list
     */
    @Test
    public void queryByIdList() {

        List<Long> idsList = Arrays.asList(1088250446457389058L, 1094592041087729666L, 1145890554823868418L);

        List<User> users = userMapper.selectBatchIds(idsList);

        users.forEach(System.out::println);
    }

    /**
     * map 类型的参数
     */
    @Test
    public void queryByMap() {

        HashMap<String, Object> map = new HashMap();

        //条件查询 map中的键就是数据库中的列(重点)
        map.put("name", "王天风");
        map.put("age", "25");
        map.put("manager_id", "1087982257332887553");


        // where name = '王天风' and age = '25'

        List<User> users = userMapper.selectByMap(map);

        users.forEach(System.out::println);

    }


    /**
     * 1.姓名中包含雨并且年龄小于40的人
     */
    @Test
    public void queryByWrapper() {

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
    public void queryByWrapper2() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 3.姓名为王姓或者年龄大于等于25,按照年龄降序排列,年龄相同的按照id升序排列
     * name like '王%' or age >=25 order by age desc,id asc
     */
    @Test
    public void queryByWrapper3() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper
                .likeRight("name", "王")
                .or()
                // >=
                .ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 4.创建日期为2019年2月14日 并且直属上级 名字为王姓的
     * "date_format(create_time,'%Y-%m-%d') and manager_id in (select id from user where name like '王%')"
     */
    @Test
    public void queryByWrapper4() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 不能直接拼接,直接拼接会产生sql注入的风险,应该使用占位符的方式
        queryWrapper
                .apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%' ");

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 5.名字为王姓并且 (年龄小于40或者邮箱不为空)
     * "name like '王%' and (age < 40 or email is not null)"
     */
    @Test
    public void queryByWrapper5() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .likeRight("name", "王").and(qw -> qw.lt("age", 40).or().isNotNull("email"));

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 6.名字为王姓 或者 (年龄小于40并且年龄大于20并且邮箱不为空)
     * "name like '王%' or (age < 40 and age >20 and email is not null)"
     */
    @Test
    public void queryByWrapper6() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .likeRight("name", "王")
                .or(
                        qw -> qw.lt("age", 40)
                                .gt("age", 20)
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
    public void queryByWrapper7() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .nested(
                        qw -> qw.lt("age", 40)
                                .or().isNotNull("email")
                )
                .likeRight("name", "王");

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    /**
     * 8.年龄为30.31.34.35
     * "age in (30,31,34,35)"
     * .in("age", Arrays.asList(30,31,34,35));
     */
    @Test
    public void queryByWrapper8() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .in("age", Arrays.asList(30, 31, 34, 35));

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 9.返回满足条件的一个数据
     * "limit 1"
     * 加到最后 last函数 .last("limit 1");
     */
    @Test
    public void queryByWrapper9() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .in("age", Arrays.asList(30, 31, 34, 35))
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
    public void queryByWrapper10() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .select("id", "name").like("name", "雨").le("age", 40);

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
    public void queryByWrapper11() {

        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .like("name", "雨").le("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    @Test
    public void queryByWrapper12Tes() {
        queryByWrapper12("强", "");
    }

    /**
     * 12.条件查询
     * "name like '%雨%' and age < 40"
     * 部分排除
     * 加到最后 last函数
     */
    public void queryByWrapper12(String name, String email) {


        //条件查询 map中的键就是数据库中的列(重点)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .like(!StringUtils.isEmpty(name), "name", name)
                .like(!StringUtils.isEmpty(email), "email", email);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    @Test
    public void testCondition() {
        String name = "";

        String email = "x";

        this.condition(name, email);

    }

    public void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();


//        没有使用mp时候的复杂写法
//        if(!StringUtils.isEmpty(name)){
//            queryWrapper.like("name",name);
//        }
//        if(!StringUtils.isEmpty(email)){
//            queryWrapper.like("email",email);
//        }

        // 使用mp之后的简化写法
        queryWrapper.like(!StringUtils.isEmpty(name), "name", name)
                .like(!StringUtils.isEmpty(email), "email", email);

        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);
    }


    @Test
    public void queryByWrapperEntity() {

        //条件查询 map中的键就是数据库中的列(重点)


        User whereUser = new User();
        whereUser.setName("刘红雨");
        whereUser.setAge(32);


        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);

        //两个查询不影响   注意条件重复问题
        queryWrapper
                .like("name", "雨").le("age", 40);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }

    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Map<String, Object> params = new HashMap<>();

        params.put("name", "王天风");
        params.put("age", null);

        // 这一句必须加,不加不生效
        //queryWrapper.allEq(params);

        // 表示为null了不忽略
        // queryWrapper.allEq(params);
        // 表示为null了忽略该条件
        // queryWrapper.allEq(params,false);


        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE age IS NULL
        // 没有name属性,因为name属性被过滤掉了
        queryWrapper.allEq((k, v) -> !k.equals("name"), params);


        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);


    }


    @Test
    public void selectByWrapperMaps() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);

        List<Map<String, Object>> userList = userMapper.selectMaps(userQueryWrapper);

        userList.forEach(System.out::println);

    }


    /**
     * 按照直属上级进行分组,查询每组的平均年龄,最大年龄,最小年龄,并且只取年龄总和小于500的组
     * <p>
     * select avg(age) avg_age ,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) < 500
     */
    @Test
    public void selectByWrapperMapsCount() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}", 500);

        List<Map<String, Object>> userList = userMapper.selectMaps(userQueryWrapper);

        userList.forEach(System.out::println);
    }


    @Test
    public void selectByWrapperObjs() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> userQueryWrapper = queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);

        /**
         * selectObjs 这个方法只查询第一列的数据,其他的数据全部被舍弃了
         */
        List<Object> userList = userMapper.selectObjs(userQueryWrapper);

        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperCount() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        /**
         * 用这个方法就不用设施查询列了,会自动count(1)
         *
         * SELECT COUNT( 1 ) FROM user WHERE name LIKE ? AND age < ?
         */
        QueryWrapper<User> userQueryWrapper = queryWrapper.like("name", "雨").lt("age", 40);

        /**
         * 查询符合条件的总记录数的
         */
        Integer integer = userMapper.selectCount(userQueryWrapper);

        System.out.println(integer);
    }


    @Test
    public void selectByWrapperOne() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        /**
         * 用这个方法就不用设施查询列了,会自动count(1)
         *
         * SELECT COUNT( 1 ) FROM user WHERE name LIKE ? AND age < ?
         */
        QueryWrapper<User> userQueryWrapper = queryWrapper.like("name", "刘红雨").lt("age", 40);

        /**
         * 查询符合条件的总记录数的
         */
        User user = userMapper.selectOne(userQueryWrapper);

        System.out.println(user);
    }

    @Test
    public void selectLambda1() {

        // 第一种构造方法
        //LambdaQueryWrapper<Object> lambdaQueryWrapper = new QueryWrapper<>().lambda();

        // 第二种构造方法
        //LambdaQueryWrapper<Object> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 第三种构造方法
        //LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        // 使用lambda表达式的好处就是防误写
        userLambdaQueryWrapper.like(User::getName, "雨").lt(User::getAge,40);

        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);

        userList.forEach(System.out::println);

    }

    /**
     * 名字为王姓并且 (年龄小于40或者邮箱不为空)
     * "name like '王%' and (age < 40 or email is not null)"
     */
    @Test
    public void selectLambda2() {

        // 第一种构造方法
        LambdaQueryWrapper<Object> lambdaQueryWrapper = new QueryWrapper<>().lambda();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        // 使用lambda表达式的好处就是防误写
        userLambdaQueryWrapper.like(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);

        userList.forEach(System.out::println);

    }


    /**
     * 3.0.7以上的版本支持 新增的条件构造器
     */
    @Test
    public void selectLambda3() {
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper).like(User::getName, "雨").ge(User::getAge, 20).list();
        userList.forEach(System.out::println);

    }


    /**
     * 3.0.7 新增的条件构造器
     */
    @Test
    public void selectLambdaMy() {
        // 第一种构造方法
        LambdaQueryWrapper<Object> lambdaQueryWrapper = new QueryWrapper<>().lambda();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        // 使用lambda表达式的好处就是防误写
        userLambdaQueryWrapper.like(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectAll(userLambdaQueryWrapper);

        userList.forEach(System.out::println);
    }
}
