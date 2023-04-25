package com.example.drivers_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class DriversMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriversMsApplication.class, args);
    }

}
