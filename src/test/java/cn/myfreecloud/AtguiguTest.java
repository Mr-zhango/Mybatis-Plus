package cn.myfreecloud;

import cn.myfreecloud.entity.Employee;
import cn.myfreecloud.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @create: 2019-10-23 11:07
 * @author: Mr.Zhang
 * @description:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtguiguTest {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 新增数据
     */
    @Test
    public void insertTest() {
        Employee employee = new Employee();
        employee.setLastName("张");
        employee.setAge(23);
        employee.setGender(1);
        employee.setEmail("65153@qq.com");

        int i = employeeMapper.insert(employee);
        System.out.println(i);
    }
}
