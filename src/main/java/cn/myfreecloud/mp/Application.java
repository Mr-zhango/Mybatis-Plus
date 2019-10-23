package cn.myfreecloud.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangyang
 * @date: 2019/7/2 10:38
 * @description:
 */
@SpringBootApplication
//@MapperScan("cn.myfreecloud.mp.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
