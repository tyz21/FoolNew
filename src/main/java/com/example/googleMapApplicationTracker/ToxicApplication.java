package com.example.googleMapApplicationTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = "*")
@SpringBootApplication
public class ToxicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToxicApplication.class, args);
    }

}
