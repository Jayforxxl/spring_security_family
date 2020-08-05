package com.jay.springsecurity;

import com.jay.springsecurity.config.RsaKeyConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Description:
 *
 * @author ZhangJieChao
 * @version 1.0
 * @date 2020/8/1 14:55
 */

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class) //将配置类加入到IOC容器中
@MapperScan("com.jay.springsecurity.mapper")
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
