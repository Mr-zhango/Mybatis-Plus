package cn.myfreecloud.mp.service.impl;

import cn.myfreecloud.mp.beans.Employee;
import cn.myfreecloud.mp.mapper.EmployeeMapper;
import cn.myfreecloud.mp.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-10-23
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
