package cn.myfreecloud.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @create: 2019-10-22 16:01
 * @author: Mr.Zhang
 * @description: MyBatisPlus配置类
 **/

@Configuration
@MapperScan("cn.myfreecloud.mapper")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        // 引入分页插件
        return new PaginationInterceptor();
    }
}
