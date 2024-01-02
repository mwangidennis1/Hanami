package com.example.hanami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HanamiApplication {

    public static void main(String[] args) {

        SpringApplication.run(HanamiApplication.class, args);
        //System.out.println(ProductRepository.class.getName());
    }

}
