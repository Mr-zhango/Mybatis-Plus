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

    /** MP默认回去查找字段为id的值作为主键,不是id的话需要标注 @TableId 这个注解**/
    @TableId
    private Long id;

    /** mp 针对实体字段和数据库字段不一样这种情况,设计了@TableField这个注解**/
    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    /**
     *  排除表中的某些字段3种方式
     *  private transient  String remark;
     *
     *   @TableField(exist = false)
     *   private static String remark;
     *
     */


    /****备注 exist = false 表示不是数据库中的字段 推荐使用 ****/
    @TableField(exist = false)
    private  String remark;
}
