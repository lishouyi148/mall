package com.mall;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mall.dao")
public class mallApplication {
    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(mallApplication.class);
        app.setAllowCircularReferences(true);  // 允许循环依赖
        app.run(args);
    }
}
