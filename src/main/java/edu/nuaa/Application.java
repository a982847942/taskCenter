package edu.nuaa;

import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Priority;

/**
 * @author brain
 * @version 1.0
 * @date 2024/5/14 14:31
 */
@SpringBootApplication
@RestController

public class Application {
    @RequestMapping(value = "/test")
    public String start(){
        return "hello";
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
//        System.out.println(BootstrapRegistryInitializer.class.getName());
        System.out.println(AutoConfigurationImportFilter.class.getName());
//        SpringApplication springApplication = new SpringApplication(Application.class);
//        springApplication.run(args);

//        AnnotationConfigServletWebServerApplicationContext ac = new AnnotationConfigServletWebServerApplicationContext(Application.class);
    }
}
