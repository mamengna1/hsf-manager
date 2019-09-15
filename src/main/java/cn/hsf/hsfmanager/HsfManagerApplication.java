package cn.hsf.hsfmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("cn.hsf.hsfmanager.mapper")   //扫描mapper
public class HsfManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsfManagerApplication.class, args);
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("D:\\software\\Tomcat\\imageTomcat\\webapps");
        return factory.createMultipartConfig();
    }
}
