package com.steel.carbon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.steel.carbon.mapper")
public class CarbonEmissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonEmissionApplication.class, args);
    }

}
