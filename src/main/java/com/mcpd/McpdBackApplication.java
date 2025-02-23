package com.mcpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class McpdBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpdBackApplication.class, args);
    }

    @RequestMapping("/mcpd")
    public String mainPage(){
        return "Municipalidad De Puerto Deseado Application";
    }

}

