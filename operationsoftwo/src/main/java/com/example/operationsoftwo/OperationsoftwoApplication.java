package com.example.operationsoftwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.operationsoftwo.mapper")
public class OperationsoftwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperationsoftwoApplication.class, args);
    }

}
