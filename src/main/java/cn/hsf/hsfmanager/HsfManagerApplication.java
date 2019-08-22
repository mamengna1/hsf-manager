package cn.hsf.hsfmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.hsf.hsfmanager.mapper")   //扫描mapper
public class HsfManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsfManagerApplication.class, args);
    }

}
