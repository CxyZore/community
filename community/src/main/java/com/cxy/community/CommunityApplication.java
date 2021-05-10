package com.cxy.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZoreCXY
 * @RestController // Spring将生成的字符串直接返回给调用者
 * @EnableTransactionManagement  // 事务注解
 */

@Component
@RestController // Spring将生成的字符串直接返回给调用者
@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class CommunityApplication {
    public static void main(String[] args) {
        // springApplication: spring应用类    作用: 用来启动springboot应用
        // 参数1: 传入入口类 类对象   参数2: main函数的参数
        SpringApplication.run(CommunityApplication.class, args);
    }

}
