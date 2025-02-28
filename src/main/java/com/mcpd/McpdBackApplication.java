package com.mcpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class McpdBackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(McpdBackApplication.class, args);
    }

    @RequestMapping("/")
    public String mainPage(){
        return "Municipalidad De Puerto Deseado Application";
    }

}

