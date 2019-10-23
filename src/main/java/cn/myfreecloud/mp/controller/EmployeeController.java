package cn.myfreecloud.mp.controller;


import cn.myfreecloud.mp.beans.Employee;
import cn.myfreecloud.mp.service.EmployeeService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-10-23
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ResponseBody
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object thisYearProject(@PathVariable("id") String id) {
        Employee employee = employeeService.getById(id);
        return employee;
    }

}

