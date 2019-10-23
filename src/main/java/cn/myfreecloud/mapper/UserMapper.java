package cn.myfreecloud.mapper;

import cn.myfreecloud.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhangyang
 * @date: 2019/7/2 10:42
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {

    /*@Select("select * from user ${ew.customSqlSegment}")*/
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    IPage<User> selectUserPage(Page<User> page,@Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
