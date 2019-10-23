package cn.myfreecloud.mp;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @create: 2019-10-23 11:07
 * @author: Mr.Zhang
 * @description:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeTest {
    @Test
    public void testGenerator() {
        // 1.全局配置
        GlobalConfig config = new GlobalConfig();

        config.setActiveRecord(false)// 是否支持AR模式
                .setAuthor("Mr.Zhang")
                .setOutputDir("C:\\develop\\IDEAProjects\\MyProjects\\Mybatis-Plus\\src\\main\\java") // 生成路径
                .setFileOverride(true) // 文件覆盖
                .setIdType(IdType.NONE)// 自增主键策略
                .setServiceName("%sService") // 设置生成service接口的名字是否是I开头的
                .setBaseResultMap(true)
                .setBaseColumnList(true);


        // 2.数据源配置

        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL) // 数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/mp?useSSL=false&serverTimezone=GMT%2B8")
                .setUsername("root")
                .setPassword("123456");


        // 3.策略配置

        StrategyConfig strategyConfig = new StrategyConfig();

        strategyConfig.setCapitalMode(true) // 设置全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("tbl_")
                // 表名
                .setInclude("tbl_employee");

        // 4.包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("cn.myfreecloud.mp")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("beans")
                .setXml("mapper");

        // 5.整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(pkConfig);

        // 6.执行
        ag.execute();


    }
}
