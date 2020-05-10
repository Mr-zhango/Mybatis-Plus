package cn.myfreecloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: zhangyang
 * @date: 2020/5/7 23:00
 * @description:
 */
@Data
public class Employee {
    @TableId
    private Long id;

    private String lastName;

    private String email;

    private Integer gender;

    private Integer age;

}
