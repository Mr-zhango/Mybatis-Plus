package cn.myfreecloud.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: zhangyang
 * @date: 2019/7/2 10:40
 * @description:
 */
@Data
@TableName("user")
public class User {

    @TableId
    private Long id;

    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;


    /****备注 exist = false 表示不是数据库中的字段****/
    @TableField(exist = false)
    private  String remark;
}
